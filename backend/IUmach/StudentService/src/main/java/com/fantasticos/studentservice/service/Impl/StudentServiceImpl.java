package com.fantasticos.studentservice.service.Impl;

import com.fantasticos.studentservice.dto.DtoRequestStudent;
import com.fantasticos.studentservice.dto.DtoStudent;
import com.fantasticos.studentservice.dto.StudentToAlgorithmDto;
import com.fantasticos.studentservice.exception.NotFounStudentException;
import com.fantasticos.studentservice.mapper.StudentMapper;
import com.fantasticos.studentservice.mapper.StudentRequestMapper;
import com.fantasticos.studentservice.model.Student;
import com.fantasticos.studentservice.repository.StudentRepository;
import com.fantasticos.studentservice.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
	
	private final static String API_KEY = "b0a5125b-86e0-4a3f-83c1-c07fd1541450";

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final StudentRequestMapper studentRequestMapper;
    private final RestTemplate restTemplate;

    @Override
    public DtoRequestStudent findById(String userName) {
        Optional<Student> student = studentRepository.findById(userName);
        return student.map(entity -> studentRequestMapper.studentToDto(student.get()))
                .orElseThrow(() -> new NotFounStudentException("El estudiante no existe"));
    }

    @Override
    public DtoRequestStudent findByEmail(String email) {
        Optional<Student> student = studentRepository.findByEmail(email);
        
        if(student.isPresent() && student.get().getVetos() >= 5)
        	throw new IllegalStateException("El usuario se encuentra baneado.");
        
        return student.map(entity -> studentRequestMapper.studentToDto(student.get()))
                .orElse(null);
    }

    @Override
    public Student save(DtoStudent dtoStudent, String bearerToken) {
        if (!studentRepository.existsById(dtoStudent.getId())) {
            StudentToAlgorithmDto sa = createStudentToAlgorithmDto(dtoStudent);
            Student student = studentMapper.DtoStudentToStudent(dtoStudent);
            student.setCreateAt(new Date());
            student.setResponsable(0L);
            student.setDetallista(0L);
            student.setColaborador(0L);
            student.setProActivo(0L);
            student.setOrganizado(0L);
            student.setConocedor(0L);
            student.setContribuciones(0L);
            student.setValoration(0L);
            student.setVetos(0L);
            RequestEntity<StudentToAlgorithmDto> request = RequestEntity.post(URI.create("http://localhost:8761/algorithm/user/cargar"))
            		.header("Authorization", bearerToken)
                    .body(sa);
            restTemplate.exchange(request, StudentToAlgorithmDto.class);
            return studentRepository.save(student);
        }
        else{
            Optional<Student> student = studentRepository.findById(dtoStudent.getId());
            student.get().setFirstName(dtoStudent.getFirstName());
            student.get().setLastName(dtoStudent.getLastName());
            student.get().setBirthday(dtoStudent.getBirthday());
            student.get().setEmail(dtoStudent.getEmail());
            student.get().setPhone(dtoStudent.getPhone());
            return studentRepository.save(student.get());
        }
    }


    private StudentToAlgorithmDto createStudentToAlgorithmDto(DtoStudent dtoStudent) {
        StudentToAlgorithmDto sa = new StudentToAlgorithmDto();
        sa.setId(dtoStudent.getId());
        sa.setPuntos(0);
        sa.setIdFacultad(dtoStudent.getIdFacultad());
        return sa;
    }

}
