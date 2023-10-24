package com.fantasticos.studentservice.service.Impl;

import java.net.URI;

import javax.transaction.Transactional;

import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fantasticos.studentservice.dto.KickDTO;
import com.fantasticos.studentservice.dto.NotificationDto;
import com.fantasticos.studentservice.dto.group.GroupToAlgorithmDto;
import com.fantasticos.studentservice.exception.GroupNotFoundException;
import com.fantasticos.studentservice.exception.KickNotFoundException;
import com.fantasticos.studentservice.exception.NotFounStudentException;
import com.fantasticos.studentservice.model.GroupEntity;
import com.fantasticos.studentservice.model.Kick;
import com.fantasticos.studentservice.model.Student;
import com.fantasticos.studentservice.repository.GroupRepository;
import com.fantasticos.studentservice.repository.HistGroupRepository;
import com.fantasticos.studentservice.repository.KickRepository;
import com.fantasticos.studentservice.repository.StudentRepository;
import com.fantasticos.studentservice.service.KickService;
import com.fantasticos.studentservice.util.Tipo;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class KickServiceImpl implements KickService {

	private final KickRepository kickRepository;
	private final GroupRepository groupRepository;
	private final StudentRepository studentRepository;
	private final RestTemplate restTemplate;
	
	private final static String API_KEY = "b0a5125b-86e0-4a3f-83c1-c07fd1541450";
	
	@Override
	public String nuevaVotacion(KickDTO modelo, String userName, String bearerToken) {
		if(kickRepository.findByIdGrupoAndIdVotado(modelo.getIdGrupo(), modelo.getIdVotado()).isPresent())
			throw new IllegalStateException("Votación en curso.");
		
		GroupEntity grupo = groupRepository.getById(modelo.getIdGrupo());
		if(grupo == null || grupo.getNumberOfStudents() <= 2) 
			throw new GroupNotFoundException("Grupo no disponible.");
		
		Student votante = studentRepository.getById(userName);
		if(votante == null || !grupo.getStudents().contains(votante) ) 
			throw new NotFounStudentException("El estudiante no existe.");
		
		Student votado = studentRepository.getById(modelo.getIdVotado());
		if(votado == null || !grupo.getStudents().contains(votado) || grupo.getIdUserAdmin().equals(modelo.getIdVotado()) )
			throw new NotFounStudentException("No es posible vetar al estudiante seleccionado");
		
		kickRepository.save(crearVotacion(grupo, votante, votado));
		notificationUser(grupo, Tipo.VOTACION, votado, votante, bearerToken);
		
		return "Votación iniciada";
	}

	@Override
	public String votar(KickDTO modelo, String userName , String bearerToken) {
		GroupEntity grupo = groupRepository.getById(modelo.getIdGrupo());
		if(grupo == null || grupo.getNumberOfStudents() <= 2) 
			throw new GroupNotFoundException("Grupo no disponible.");
		
		Student votante = studentRepository.getById(userName);
		if(votante == null || !grupo.getStudents().contains(votante) ) 
			throw new NotFounStudentException("El estudiante no existe.");
		
		Kick votacion = kickRepository.findByIdGrupoAndIdVotado(grupo.getId(), modelo.getIdVotado())
				.orElseThrow(KickNotFoundException::new);
		
		if(votacion.getVotantes().contains(votante))
			throw new IllegalStateException("Ya votó");
		
		votacion.getVotantes().add(votante);
		
		if(votacion.getVotantes().size() == grupo.getStudents().size() - 1) {
			Student votado = studentRepository.getById(votacion.getIdVotado());
			
			if(purgarGrafo(grupo.getId(), votado.getId(), bearerToken)) {				
				notificationUser(grupo, Tipo.EXPULSION, votado, votante, bearerToken);
				vetar(grupo, votado);
				kickRepository.delete(kickRepository.getById(votacion.getId()));
			}
		}else {			
			kickRepository.save(votacion);
		}		
		
		return "Voto efectuado.";
	}
	
	@Override
	public String rechazar(KickDTO modelo, String userName) {
		GroupEntity grupo = groupRepository.getById(modelo.getIdGrupo());
		if(grupo == null) 
			throw new GroupNotFoundException("Grupo no disponible.");
		
		Student votante = studentRepository.getById(userName);
		if(votante == null || !grupo.getStudents().contains(votante) ) 
			throw new NotFounStudentException("El estudiante no existe.");
		
		Kick votacion = kickRepository.findByIdGrupoAndIdVotado(grupo.getId(), modelo.getIdVotado())
				.orElseThrow(KickNotFoundException::new);
		
		kickRepository.delete(votacion);
		
		return "Votación cancelada";
	}
	
	private Boolean purgarGrafo(Long idGroup, String idUser, String bearerToken) {
		GroupToAlgorithmDto dto = new GroupToAlgorithmDto();
        dto.setIdGrupo(idGroup);
        
        RequestEntity<GroupToAlgorithmDto> request = RequestEntity.put(URI.create("http://localhost:8761/algorithm/group/remover-miembro"))
        		.header("Authorization", bearerToken)
                .body(dto);
        try{
        	restTemplate.exchange(request, String.class);
        	return true;
        }catch(Exception e) {
        	return false;
        }
	}
	
	private void vetar(GroupEntity grupo, Student votado) {
		grupo.getStudents().remove(votado);
		grupo.setNumberOfStudents(grupo.getNumberOfStudents() - 1);
		votado.setVetos(votado.getVetos() != null ? votado.getVetos() : 0  + 1);
		
		groupRepository.save(grupo);
		studentRepository.save(votado);
	}

	private Kick crearVotacion(GroupEntity grupo, Student votante, Student votado) {
		Kick entidad = new Kick();
		
		entidad.setIdGrupo(grupo.getId());
		entidad.setIdVotado(votado.getId());
		entidad.setIdVotante(votante.getId());
		entidad.getVotantes().add(votante);
		
		return entidad;
	}
	
	private void notificationUser(GroupEntity group, Tipo typeNotification, Student studentVetado, Student votante, String bearerToken) {
        NotificationDto notificationDto = new NotificationDto();
        notificationDto.setIdGroup(group.getId());
        notificationDto.setTipoNotification(typeNotification);
        notificationDto.setNameGroup(group.getName());
        notificationDto.setNameNotificante(studentVetado.getFirstName() + " " + studentVetado.getLastName());
        
        System.out.println(group.getStudents().size());
        
        for (Student s : group.getStudents()) {
        	if(typeNotification.equals(Tipo.VOTACION) && (s.getId().equals(studentVetado.getId()) || s.getId().equals(votante.getId()))) continue;
            notificationDto.setIdStudent(studentVetado.getId());
            notificationDto.setIdStudentNotification(s.getId());

            RequestEntity<NotificationDto> request = RequestEntity.post(URI.create("http://localhost:8761/notification/create-notification"))
                    .header("Authorization", bearerToken)
                    .body(notificationDto);
            restTemplate.exchange(request, NotificationDto.class);

        }
    }

}
