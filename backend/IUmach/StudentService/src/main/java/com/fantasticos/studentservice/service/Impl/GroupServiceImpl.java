package com.fantasticos.studentservice.service.Impl;

import com.fantasticos.studentservice.dto.DtoValorationCreatedRequest;
import com.fantasticos.studentservice.dto.FilterDto;
import com.fantasticos.studentservice.dto.HistDto;
import com.fantasticos.studentservice.dto.NotificationDto;
import com.fantasticos.studentservice.dto.group.*;
import com.fantasticos.studentservice.exception.GroupExistException;
import com.fantasticos.studentservice.exception.GroupJoinException;
import com.fantasticos.studentservice.exception.GroupNotFoundException;
import com.fantasticos.studentservice.mapper.GroupMapper;
import com.fantasticos.studentservice.mapper.StudentMapper;
import com.fantasticos.studentservice.model.GroupEntity;
import com.fantasticos.studentservice.model.HistGroup;
import com.fantasticos.studentservice.model.RequestJoin;
import com.fantasticos.studentservice.model.Student;
import com.fantasticos.studentservice.repository.GroupRepository;
import com.fantasticos.studentservice.repository.RequestJoinRepository;
import com.fantasticos.studentservice.repository.StudentRepository;
import com.fantasticos.studentservice.service.GroupService;
import com.fantasticos.studentservice.util.Tipo;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.header.Header;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final GroupRepository repository;
    private final StudentRepository studentRepository;
    private final GroupMapper mapper;
    private final StudentMapper studentMapper;
    private final RestTemplate restTemplate;
    private final RequestJoinRepository requestJoinRepository;

    private final static String API_KEY = "b0a5125b-86e0-4a3f-83c1-c07fd1541450";

    @Override
    public GroupDto findById(Long id) {
        Optional<GroupEntity> group = repository.findById(id);
        return group.map(entity -> mapper.groupToGroupDto(group.get()))
                .orElseThrow(() -> new GroupNotFoundException("The group does not exist"));
    }

    @Override
    public List<GroupDto> findAll() {
        List<GroupEntity> groupList = repository.findAll();
        if (groupList.isEmpty())
            throw new GroupNotFoundException("There were no groups found");
        return mapper.groupListToGroupListDto(groupList);
    }

    @Override
    public void save(GroupCreateDto groupToCreate, String userName, String bearerToken) {
        if (exist(groupToCreate))
            throw new GroupExistException("The group already exist");
        else {

            GroupEntity group = mapper.groupCreateDtoToGroupEntity(groupToCreate);
            group.getStudents().add(getStudent(userName));
            group.setIdUserAdmin(userName);
            group.setNumberOfStudents(1);
            repository.save(group);
            GroupToAlgorithmDto ga = createGroupToAlgorithm(group, userName);
            ga.setIdMateria(groupToCreate.getIdSubject());

            RequestEntity<GroupToAlgorithmDto> request = RequestEntity.post(URI.create("http://localhost:8761/algorithm/group/cargar"))
            		.header("Authorization", bearerToken)
                    .body(ga);
            restTemplate.exchange(request, GroupToAlgorithmDto.class);

        }

    }

    private Student getStudent(String userName) {
        Optional<Student> student = studentRepository.findById(userName);
        return student.flatMap(entity -> student).orElseThrow(() -> new IllegalStateException("The student does not exist"));
    }


    private GroupToAlgorithmDto createGroupToAlgorithm(GroupEntity group, String userName) {
        GroupToAlgorithmDto ga = new GroupToAlgorithmDto();
        ga.setIdGrupo(group.getId());
        ga.setIdAdmin(userName);
        return ga;
    }

    private boolean exist(GroupCreateDto groupToCreate) {
        return repository.findByNameAndDescriptionAndCareerAndSubject(
                groupToCreate.getName(), groupToCreate.getDescription(),
                groupToCreate.getCareer(), groupToCreate.getSubject()
        ).isPresent();
    }

    @Override
    public void update(Long id, GroupUpdateDto groupToUpdate) {
        Optional<GroupEntity> wantedGroup = repository.findById(id);
        if (wantedGroup.isEmpty())
            throw new GroupNotFoundException("The group you want to update does not exist");
        if (!sameValues(wantedGroup.get(), groupToUpdate)) {
            if (existAnotherGroupWithThoseValues(id, groupToUpdate)) {
                throw new GroupExistException("The group was not updated because another one already has those identification values");
            }
            GroupEntity groupUpdated = mapper.merge(wantedGroup.get(), groupToUpdate);
            repository.save(groupUpdated);
        }
    }

    private boolean sameValues(GroupEntity wantedGroup, GroupUpdateDto groupToUpdate) {
        return wantedGroup.getName().equals(groupToUpdate.getName()) &&
                wantedGroup.getDescription().equals(groupToUpdate.getDescription()) &&
                (wantedGroup.getCareer() == null || wantedGroup.getCareer().equals(groupToUpdate.getCareer()) ) &&
                (wantedGroup.getCareer() == null ||wantedGroup.getSubject().equals(groupToUpdate.getSubject()) ) &&
                wantedGroup.getClosed().equals(groupToUpdate.getClosed());
    }

    private boolean existAnotherGroupWithThoseValues(Long id, GroupUpdateDto groupToUpdate) {
        return repository.findByIdNotAndNameAndDescriptionAndCareerAndSubject(id,
                groupToUpdate.getName(), groupToUpdate.getDescription(), groupToUpdate.getCareer(),
                groupToUpdate.getSubject()).isPresent();
    }

    @Override
    public void delete(Long id, String bearerToken, String idUser) {
        Optional<GroupEntity> group = repository.findById(id);
        if(group.isEmpty())
            throw new GroupNotFoundException("The group you want to delete does not exist");
        if(!idUser.equals(group.get().getIdUserAdmin()))
            throw new IllegalStateException("The user cannot delete the group because he is not the admin");
       
        Gson jsonParser = new Gson();
        GroupToAlgorithmDto dto = new GroupToAlgorithmDto();
        dto.setIdGrupo(id);
        
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", bearerToken);
        headers.add("Content-Type", "application/json");
        
        String jsonPayload  = jsonParser.toJson(dto);
        String url = "http://localhost:8761/algorithm/group/borrar";
        
        HttpEntity<String> entity = new HttpEntity<String>(jsonPayload.toString(), headers);

        ResponseEntity<String> resp = restTemplate.exchange(url, HttpMethod.DELETE, entity, String.class);
        
        if(resp.getStatusCode() == HttpStatus.OK) {
        	 notificationUser(group.get(), Tipo.CIERRE, null, bearerToken);
        	 valorateUsers(group.get(), bearerToken);
        	 createHistory(group.get(), bearerToken);
        	 repository.deleteById(id);        
        }
        
    }
    
    private void createHistory(GroupEntity group, String bearerToken) {
    	HistDto histDto = new HistDto();
    	histDto.setId(group.getId());

        RequestEntity<HistDto> request = RequestEntity.post(URI.create("http://localhost:8761/group/hist/cargar"))
                .header("Authorization", bearerToken)
                .body(histDto);
        restTemplate.exchange(request, HistGroup.class);
	}

    private void valorateUsers(GroupEntity group, String bearerToken) {
		DtoValorationCreatedRequest valorationDTO = new DtoValorationCreatedRequest();
        valorationDTO.setNameGroup(group.getName());
        valorationDTO.setIdStudent(group.getStudents().stream().map(m -> {return m.getId();}).collect(Collectors.toList()));
        
        RequestEntity<DtoValorationCreatedRequest> request = RequestEntity.post(URI.create("http://localhost:8761/valoration/create-valoration"))
                .header("Authorization", bearerToken)
                .body(valorationDTO);
        restTemplate.exchange(request, DtoValorationCreatedRequest.class);
		
	}

	private void notificationUser(GroupEntity group, Tipo typeNotification, String idUserJoin, String bearerToken) {
        NotificationDto notificationDto = new NotificationDto();
        notificationDto.setIdGroup(group.getId());
        notificationDto.setTipoNotification(typeNotification);
        notificationDto.setNameGroup(group.getName());
        if (idUserJoin == null) {
            notificationDto.setNameNotificante(getStudent(group.getIdUserAdmin()).getFirstName()
                    + " " + getStudent(group.getIdUserAdmin()).getLastName());
        } else {
            notificationDto.setNameNotificante(getStudent(idUserJoin).getFirstName()
                    + " " + getStudent(idUserJoin).getLastName());
        }
        for (Student s : group.getStudents()) {
            notificationDto.setIdStudent(s.getId());
            notificationDto.setIdStudentNotification(s.getId());

            RequestEntity<NotificationDto> request = RequestEntity.post(URI.create("http://localhost:8761/notification/create-notification"))
                    .header("Authorization", bearerToken)
                    .body(notificationDto);
            restTemplate.exchange(request, NotificationDto.class);

        }
    }


    @Override
    public void joinGroup(String idUser, Long idGroup, String bearerToken) {
        Optional<GroupEntity> group = repository.findById(idGroup);
        Student student = getStudent(idUser);
        if (group.isEmpty())
            throw new GroupNotFoundException("The group does not exist");

        if (isValidToJoin(group.get(), student)) {
            group.get().setNumberOfStudents(group.get().getNumberOfStudents() + 1);
            group.get().getStudents().add(student);
            
            GroupToAlgorithmDto ga = createGroupToAlgorithmJoin(group.get(), idUser);

            RequestEntity<GroupToAlgorithmDto> request = RequestEntity.put(URI.create("http://localhost:8761/algorithm/group/agregar-miembro"))
            		.header("Authorization", bearerToken)
                    .body(ga);
            restTemplate.exchange(request, GroupToAlgorithmDto.class);
            repository.save(group.get());
            notificationUser(group.get(), Tipo.UNION, idUser, bearerToken);
        }
    }

    private GroupToAlgorithmDto createGroupToAlgorithmJoin(GroupEntity group, String userName) {
        GroupToAlgorithmDto ga = new GroupToAlgorithmDto();
        ga.setIdGrupo(group.getId());
        ga.setIdMiembro(userName);
        return ga;
    }

    private boolean isValidToJoin(GroupEntity group, Student student) {

        for (Student s : group.getStudents()) {
            if (s.getId().equals(student.getId())) {
                throw new GroupJoinException("Unable to join because student is already a member");
            }
        }

        return isOpen(group, student);
    }

    private boolean isOpen(GroupEntity group, Student student) {
        if (group.getClosed()) {
            if (!accepted(group, student))
                throw new GroupJoinException("Could not join because the group is closed");
        }
        return cantMembersValid(group);
    }

    private boolean accepted(GroupEntity group, Student student) {
        Optional<RequestJoin> request = requestJoinRepository.findByStudentAndGroup(student, group);
        if(request.isEmpty())
            throw new IllegalStateException("The request could not be found");
        return request.get().getAccepted();
    }

    private boolean cantMembersValid(GroupEntity group) {
        if (group.getNumberOfStudents() > group.getMaxNumberOfStudents())
            throw new GroupJoinException("Could not join because there is no more space in the group");
        return true;
    }

    @Override
    public List<GroupDto> findMyGroups(String idUser) {
        Student student = getStudent(idUser);
        Set<GroupEntity> groupList = repository.getByStudent(idUser);
        if (groupList.isEmpty())
            throw new GroupExistException("User has no groups");
        return mapper.toGroupListDto(groupList);
    }

    @Override
    public List<RecommendedGroupDto> findRecommendedGroups(FilterDto search, String idUser) {
        String url = getUrl(search);
        RequestEntity<Void> request = RequestEntity
                .get(URI.create(url))
                .build();
        ResponseEntity<GrupoCoincidenciaDTO[]> response = restTemplate.exchange(request, GrupoCoincidenciaDTO[].class);
        GrupoCoincidenciaDTO[] list = response.getBody();
        return findGroups(list);
    }
    
    @Override
	public List<RecommendedStudentDto> findRecommendedStudents(FilterDto search, String idUser) {
    	String url = getUrl(search);
    	
    	RequestEntity<Void> request = RequestEntity
                .get(URI.create(url))
                .build();
        ResponseEntity<EstudianteCoincidenciaDTO[]> response = restTemplate.exchange(request, EstudianteCoincidenciaDTO[].class);
        EstudianteCoincidenciaDTO[] list = response.getBody();
		return findStudents(list);
	}

	private String getUrl(FilterDto search) {
        String url = "";
        
        if(search.getGroup() != null)
        	url = "http://localhost:8761/algorithm/buscar-estudiantes?group=" + search.getGroup() + "&pagina=" + search.getPagina();
        else
        	url = "http://localhost:8761/algorithm/buscar-grupos?user=" + search.getUser() + "&pagina=" + search.getPagina();
        	
        if(search.getTag() != null)
            url += "&tag=" + search.getTag();
        if(search.getMateria() != null)
            url += "&materia=" + search.getMateria();

        return url;
    }

    @Override
    public void leaveGroup(String idUser, Long idGroup, String bearerToken) {
        GroupEntity group = repository.findById(idGroup)
                .orElseThrow(() -> new GroupNotFoundException("The group does not exist"));
        Student student = getStudent(idUser);
        
        if (!group.getStudents().contains(student))
            throw new IllegalStateException("The student is not in the group");
        if (group.getStudents().size() <= 1) {
        	delete(idGroup, bearerToken, idUser);
        	return;
        }
        
        group.getStudents().remove(student);
        group.setNumberOfStudents(group.getNumberOfStudents()-1);
        
        
        GroupToAlgorithmDto dto = new GroupToAlgorithmDto();
        dto.setIdGrupo(idGroup);
        
        RequestEntity<GroupToAlgorithmDto> request = RequestEntity.put(URI.create("http://localhost:8761/algorithm/group/remover-miembro"))
        		.header("Authorization", bearerToken)
                .body(dto);
        ResponseEntity<String> resp = restTemplate.exchange(request, String.class);
        
        if(idUser.equals(group.getIdUserAdmin()))
          group.setIdUserAdmin(new Gson().fromJson(resp.getBody(), JsonObject.class)
        		  .get("administrador").getAsJsonObject()
        		  .get("parKey").getAsString());
        
        if(resp.getStatusCode() == HttpStatus.OK) {
        	notificationUser(group, Tipo.SALIR, idUser, bearerToken);
            repository.save(group);   
        }
        
    }

    private List<RecommendedGroupDto> findGroups(GrupoCoincidenciaDTO[] list) {
        return Arrays.stream(list).map(o -> {
            RecommendedGroupDto rgdto = new RecommendedGroupDto();
            repository.findById(o.getIdGrupo())
                    .ifPresent(g -> {
                        rgdto.setName(g.getName());
                        rgdto.setImage(g.getImage());
                        rgdto.setDescription(g.getDescription());
                        rgdto.setClosed(g.getClosed());
                        rgdto.setMaxNumberOfStudents(g.getMaxNumberOfStudents());
                        rgdto.setNumberOfStudents(g.getNumberOfStudents());
                        rgdto.setShift(g.getShift());
                        rgdto.setCareer(g.getCareer());
                        rgdto.setSubject(g.getSubject());
                        rgdto.setStudents(g.getStudents().stream().map(studentMapper::studentToDto).collect(Collectors.toSet()));
                    });
            rgdto.setIdGrupo(o.getIdGrupo());
            rgdto.setTags(o.getTags());
            rgdto.setCodigoMateria(o.getCodigoMateria());
            rgdto.setNameMateria(o.getNameMateria());
            rgdto.setCoincidencia(o.getCoincidencia());

            return rgdto;
        }).collect(Collectors.toList());
    }


    private List<RecommendedStudentDto> findStudents(EstudianteCoincidenciaDTO[] list) {
    	return Arrays.stream(list).map(o -> {
    		RecommendedStudentDto rsdto = new RecommendedStudentDto();
    		 studentRepository.findById(o.getIdEstudiante())
             .ifPresent(e -> {
                 rsdto.setFirstName(e.getFirstName());
                 rsdto.setLastName(e.getLastName());
                 rsdto.setBirthday(e.getBirthday());
                 rsdto.setValoration(e.getValoration());
                 rsdto.setEmail(e.getEmail());
                 rsdto.setPhone(e.getPhone());
             });
    		 
    		 rsdto.setIdEstudiante(o.getIdEstudiante());
    		 rsdto.setTags(o.getTags());
    		 rsdto.setMaterias(o.getMaterias());
    		 rsdto.setCarreras(o.getCarreras());
    		 rsdto.setFacultad(o.getFacultad());
    		 rsdto.setCoincidencia(o.getCoincidencia());
    		return rsdto;
    	}).collect(Collectors.toList());
	}
	
}
