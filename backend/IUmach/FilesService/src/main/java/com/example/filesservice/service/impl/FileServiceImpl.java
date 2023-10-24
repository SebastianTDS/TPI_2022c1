package com.example.filesservice.service.impl;


import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.filesservice.dto.*;
import com.example.filesservice.mapper.FileMapper;
import com.example.filesservice.repository.FileRepository;
import com.example.filesservice.service.FileService;
import com.example.filesservice.util.Tipo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.*;

import static com.example.filesservice.util.GroupApi.getGroupById;
import static com.example.filesservice.util.StudentApi.getUserById;


@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final AmazonS3 amazonS3;

    private final FileRepository fileRepository;

    private final FileMapper fileMapper;

    @Value("${BUCKET_NAME}")
    private String bucketName;
    

	@Override
	public Long checkSpace(Long idGroup) {
		return fileRepository.getGroupRepoSpace(idGroup);
	}


    @Override
    public com.example.filesservice.model.File uploadFile(FileGroupRequestDto fileGroupRequestDto, String bearerToken,Boolean isFile) {
         Optional<StudentRequestDto> studentRequestDto = getUserById(bearerToken, fileGroupRequestDto.getIdUser());
         Optional<GroupResponseDto> group = getGroupById(bearerToken, fileGroupRequestDto.getIdGroup());
        try {
            return uploadGroupAndSaveFile2(fileGroupRequestDto,isFile,studentRequestDto.get(),bearerToken,group.get());
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar el archivo");
        }
    }

    @Override
    public com.example.filesservice.model.File uploadGroupImageProfile(FileGroupRequestDto fileGroupRequestDto, String bearerToken,Boolean isFile) {
        Optional<StudentRequestDto> studentRequestDto=getUserById(bearerToken, fileGroupRequestDto.getIdUser());
        getGroupById(bearerToken, fileGroupRequestDto.getIdGroup());
        try {
            com.example.filesservice.model.File file=fileRepository.findByIdGroupAndIsFileFalse(fileGroupRequestDto.getIdGroup());
            if(file!=null){
                amazonS3.deleteObject(new DeleteObjectRequest(bucketName, file.getName()));
                fileRepository.delete(file);
            }
            if (!isValid(StringUtils.getFilenameExtension(fileGroupRequestDto.getFile().getOriginalFilename()))) {
                throw new RuntimeException("Error formato no soportado");
            }
           return uploadGroupAndSaveFile(fileGroupRequestDto,isFile,studentRequestDto.get());
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar la imagen");
        }
    }

    @Override
    public com.example.filesservice.model.File uploadImageProfile(FileRequestDto fileRequestDto, String bearerToken) {

        getUserById(bearerToken, fileRequestDto.getIdUser());

        try {
            com.example.filesservice.model.File file=fileRepository.findByIdUserAndIdGroupIsNullAndIsFileIsFalse(fileRequestDto.getIdUser());
            if(file!=null){
                amazonS3.deleteObject(new DeleteObjectRequest(bucketName, file.getName()));
                fileRepository.delete(file);
            }
            if (!isValid(StringUtils.getFilenameExtension(fileRequestDto.getFile().getOriginalFilename()))) {
                throw new RuntimeException("Error formato no soportado");
            }
            return uploadImageAndSaveFile(fileRequestDto);
        }catch(IOException e){
                throw new RuntimeException("Error al guardar la imagen");
            }

        }

    private com.example.filesservice.model.File uploadImageAndSaveFile(FileRequestDto fileRequestDto) throws IOException {
        final File file = convertMultipartFileToFile(fileRequestDto.getFile());
        final String fileName = LocalDateTime.now() + "_" + UUID.randomUUID() + file.getName();
        final PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName, file);
        amazonS3.putObject(putObjectRequest);
        com.example.filesservice.model.File fileEntity = new com.example.filesservice.model.File();
        fileEntity.setIdUser(fileRequestDto.getIdUser());
        fileEntity.setUserName(fileRequestDto.getUserName());
        fileEntity.setName(fileName);
        fileEntity.setIsFile(false);
        Files.delete(file.toPath());
        return fileRepository.save(fileEntity);
    }

    @Override
    public List<FileResponseDto> getGroupFiles(Long id, String bearerToken) {
        Optional<GroupResponseDto> group = getGroupById(bearerToken,id);
        List<FileResponseDto> filesList= new ArrayList<>();
            List<com.example.filesservice.model.File> files = fileRepository.findAllByIdGroupAndIsFileTrue(group.get().getId());
            files.forEach(file -> { FileResponseDto fileResponseDto = fileMapper.fileToFileResponseDto(file);
                                    fileResponseDto.setUrl(getUrlFile(file));
                                    filesList.add(fileResponseDto);
            }
            );

        return filesList;
    }

    @Override
    public FileResponseDto getUrlImageProfile(String id, String bearerToken) {

        getUserById(bearerToken, id);
        com.example.filesservice.model.File file=fileRepository.findByIdUserAndIdGroupIsNullAndIsFileIsFalse(id);
        FileResponseDto fileResponseDto = new FileResponseDto();
        fileResponseDto.setUrl(getUrlFile(file));
        return fileResponseDto;
    }

    @Override
    public FileResponseDto getGroupUrlImageProfile(Long id, String bearerToken) {
        getGroupById(bearerToken, id);
        com.example.filesservice.model.File file=fileRepository.findByIdGroupAndIsFileFalse(id);
        FileResponseDto fileResponseDto = new FileResponseDto();
        fileResponseDto.setUrl(getUrlFile(file));
        return fileResponseDto;
    }

    @Override
    public void deleteGroupFile(FileRequestDto fileRequestDto, String bearerToken) {
        getGroupById(bearerToken, fileRequestDto.getIdGroup());
        com.example.filesservice.model.File file=fileRepository.findByName(fileRequestDto.getName());
        if(file!=null) {
            amazonS3.deleteObject(new DeleteObjectRequest(bucketName, file.getName()));
            fileRepository.delete(file);
        }else{
            throw new RuntimeException("El archivo a eliminar no existe en la base de datos");
        }
    }


    private File convertMultipartFileToFile(final MultipartFile multipartFile) {
        File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        try (final FileOutputStream outputStream = new FileOutputStream(file)) {
            outputStream.write(multipartFile.getBytes());
        } catch (final IOException e) {
            throw new RuntimeException("Error al convertir el archivo multipartFile a file");
        }
        return file;
    }


    private com.example.filesservice.model.File uploadGroupAndSaveFile2(FileGroupRequestDto fileGroupRequestDto, Boolean isFile, StudentRequestDto studentRequestDto, String bearerToken, GroupResponseDto groupResponseDto) throws IOException {
        final File file = convertMultipartFileToFile(fileGroupRequestDto.getFile());
        final String fileName = LocalDateTime.now() + "_" + UUID.randomUUID() + file.getName();
        final PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName, file);
        
        checkRepoSpace(file.length(), fileGroupRequestDto.getIdGroup());
        
        amazonS3.putObject(putObjectRequest);
        com.example.filesservice.model.File fileEntity = new com.example.filesservice.model.File();
        fileEntity.setIdUser(fileGroupRequestDto.getIdUser());
        fileEntity.setUserName(studentRequestDto.getFirstName()+' '+studentRequestDto.getLastName());
        fileEntity.setName(fileName);
        fileEntity.setIdGroup(fileGroupRequestDto.getIdGroup());
        fileEntity.setIsFile(isFile);
        fileEntity.setSize(file.length());
        fileEntity.setOriginalName(file.getName());
        fileEntity.setExtension(StringUtils.getFilenameExtension(fileGroupRequestDto.getFile().getOriginalFilename()));
        Files.delete(file.toPath());
        sendNotification(bearerToken,studentRequestDto,groupResponseDto);
        return fileRepository.save(fileEntity);
    }

    private void checkRepoSpace(Long length, Long idGroup) {
    	Long size = fileRepository.getGroupRepoSpace(idGroup);
    	size = size == null ? 0 : size;
    	
		if( size + length > 52428800L)
			throw new OutOfMemoryError("Se excedió el límite de grupo");
	}

	private com.example.filesservice.model.File uploadGroupAndSaveFile(FileGroupRequestDto fileGroupRequestDto,Boolean isFile,StudentRequestDto studentRequestDto) throws IOException {
        final File file = convertMultipartFileToFile(fileGroupRequestDto.getFile());
        final String fileName = LocalDateTime.now() + "_" + UUID.randomUUID() + file.getName();
        final PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName, file);
        amazonS3.putObject(putObjectRequest);
        com.example.filesservice.model.File fileEntity = new com.example.filesservice.model.File();
        fileEntity.setIdUser(fileGroupRequestDto.getIdUser());
        fileEntity.setUserName(studentRequestDto.getFirstName()+' '+studentRequestDto.getLastName());
        fileEntity.setName(fileName);
        fileEntity.setIdGroup(fileGroupRequestDto.getIdGroup());
        fileEntity.setIsFile(isFile);
        fileEntity.setOriginalName(file.getName());
        fileEntity.setExtension(StringUtils.getFilenameExtension(fileGroupRequestDto.getFile().getOriginalFilename()));
        Files.delete(file.toPath());
        return fileRepository.save(fileEntity);
    }
    private boolean isValid(String extension) {
        List<String> validExtensions = Arrays.asList("jpeg", "jpg", "png");
        return validExtensions.contains(extension);
    }

    private String getUrlFile(com.example.filesservice.model.File file) {
        amazonS3.setObjectAcl(bucketName, file.getName(), CannedAccessControlList.PublicRead);
        URL url = amazonS3.getUrl(bucketName, file.getName());
        return url.toString();
    }

    private void sendNotification(String bearerToken, StudentRequestDto student,GroupResponseDto group) {
        NotificationDto notificationDto = new NotificationDto();
        notificationDto.setIdGroup(group.getId());
        notificationDto.setTipoNotification(Tipo.ARCHIVO);
        notificationDto.setNameGroup(group.getName());
        notificationDto.setNameNotificante(student.getFirstName()
                + " " + student.getLastName());
        for (StudentRequestDto s : group.getStudents()) {
            notificationDto.setIdStudent(s.getId());
            notificationDto.setIdStudentNotification(s.getId());

            createNotification(bearerToken, notificationDto);
        }
    }


    public static void createNotification(String bearerToken, NotificationDto notificationDto) {

        RestTemplate restTemplate = new RestTemplate();
        RequestEntity<NotificationDto> request = RequestEntity.post(URI.create("http://localhost:8761/notification/create-notification"))
                .header("Authorization", bearerToken)
                .body(notificationDto);
        restTemplate.exchange(request, NotificationDto.class);
    }
}

