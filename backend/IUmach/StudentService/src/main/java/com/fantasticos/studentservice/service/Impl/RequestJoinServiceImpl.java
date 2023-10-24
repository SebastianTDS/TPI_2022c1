package com.fantasticos.studentservice.service.Impl;

import com.fantasticos.studentservice.dto.NotificationDto;
import com.fantasticos.studentservice.dto.request.RequestJoinDataDto;
import com.fantasticos.studentservice.dto.request.RequestJoinDto;
import com.fantasticos.studentservice.exception.RequestJoinFoundException;
import com.fantasticos.studentservice.exception.RequestJoinNotFoundException;
import com.fantasticos.studentservice.mapper.GroupMapper;
import com.fantasticos.studentservice.mapper.RequestJoinMapper;
import com.fantasticos.studentservice.mapper.StudentMapper;
import com.fantasticos.studentservice.mapper.StudentRequestMapper;
import com.fantasticos.studentservice.model.GroupEntity;
import com.fantasticos.studentservice.model.RequestJoin;
import com.fantasticos.studentservice.model.Student;
import com.fantasticos.studentservice.repository.RequestJoinRepository;
import com.fantasticos.studentservice.service.GroupService;
import com.fantasticos.studentservice.service.RequestJoinService;
import com.fantasticos.studentservice.service.StudentService;
import com.fantasticos.studentservice.util.Tipo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RequestJoinServiceImpl implements RequestJoinService {

    private final RequestJoinRepository repository;
    private final RequestJoinMapper requestJoinMapper;
    private final StudentRequestMapper studentRequestMapper;
    private final GroupMapper groupMapper;
    private final StudentService studentService;
    private final GroupService groupService;
    private final RestTemplate restTemplate;

    @Override
    public RequestJoinDto findById(Long id) {
        return repository.findById(id).map(requestJoinMapper::requestJoinToRequestJoinDto)
                .orElseThrow(() -> new RequestJoinNotFoundException("The request join was not found"));
    }

    @Override
    public List<RequestJoinDto> findAll() {
        List<RequestJoin> list = repository.findAll();
        if (list.isEmpty())
            throw new RequestJoinNotFoundException("No requests join found");
        return requestJoinMapper.requestJoinListToRequestJoinDtoList(list);
    }

    @Override
    public void save(RequestJoinDataDto requestJoinDataDto, String userName, String bearerToken) {
        Student student = studentRequestMapper
                .DtoStudentToStudent(studentService.findById(userName));
        GroupEntity group = groupMapper
                .groupDtoToGroupEntity(groupService.findById(requestJoinDataDto.getGroupId()));
        if (exist(student, group))
            throw new RequestJoinFoundException("The request join already exist");
        notificationAdmin(group,Tipo.SOLICITUD, student, bearerToken);
        repository.save(createRequest(student, group));
    }
    
    @Override
	public void inviteEmail(RequestJoinDataDto requestJoinDataDto, String userName, String bearerToken) {
    	Student student = studentRequestMapper.DtoStudentToStudent(studentService.findByEmail(requestJoinDataDto.getUsuario()));
    	GroupEntity group = groupMapper
                .groupDtoToGroupEntity(groupService.findById(requestJoinDataDto.getGroupId()));
    	if (exist(student, group))
            throw new RequestJoinFoundException("The request join already exist");
        if (!group.getIdUserAdmin().equals(userName))
        	throw new IllegalStateException("Only the administrator can send an invite");
        notificationUser(group,Tipo.INVITACION, student, bearerToken);
        repository.save(createRequest(student, group));
	}
    
    @Override
	public void invite(RequestJoinDataDto requestJoinDataDto, String userName, String bearerToken) {
    	Student student = studentRequestMapper.DtoStudentToStudent(studentService.findById(requestJoinDataDto.getUsuario()));
    	GroupEntity group = groupMapper
                .groupDtoToGroupEntity(groupService.findById(requestJoinDataDto.getGroupId()));
        if (exist(student, group))
            throw new RequestJoinFoundException("The request join already exist");
        if (!group.getIdUserAdmin().equals(userName))
        	throw new IllegalStateException("Only the administrator can send an invite");
        notificationUser(group,Tipo.INVITACION, student, bearerToken);
        repository.save(createRequest(student, group));
	}

    private void notificationAdmin(GroupEntity group, Tipo typeNotification, Student student, String bearerToken) {
        NotificationDto notificationDto = new NotificationDto();
        notificationDto.setIdGroup(group.getId());
        notificationDto.setTipoNotification(typeNotification);
        notificationDto.setNameGroup(group.getName());
        notificationDto.setNameNotificante(student.getFirstName()
                + " " + student.getLastName());

        notificationDto.setIdStudent(student.getId());
        notificationDto.setIdStudentNotification(group.getIdUserAdmin());

        RequestEntity<NotificationDto> request = RequestEntity.post(URI.create("http://localhost:8761/notification/create-notification"))
                .header("Authorization", bearerToken)
                .body(notificationDto);
        restTemplate.exchange(request, NotificationDto.class);

    }
    
    private void notificationUser(GroupEntity group, Tipo typeNotification, Student student, String bearerToken) {
        NotificationDto notificationDto = new NotificationDto();
        notificationDto.setIdGroup(group.getId());
        notificationDto.setTipoNotification(typeNotification);
        notificationDto.setNameGroup(group.getName());
        notificationDto.setNameNotificante("Admin");

        notificationDto.setIdStudent(group.getIdUserAdmin());
        notificationDto.setIdStudentNotification(student.getId());

        RequestEntity<NotificationDto> request = RequestEntity.post(URI.create("http://localhost:8761/notification/create-notification"))
                .header("Authorization", bearerToken)
                .body(notificationDto);
        restTemplate.exchange(request, NotificationDto.class);

    }

    private RequestJoin createRequest(Student student, GroupEntity group) {
        RequestJoin requestJoin = new RequestJoin();
        requestJoin.setStudent(student);
        requestJoin.setGroup(group);
        return requestJoin;
    }

    private boolean exist(Student student, GroupEntity group) {
        return repository.findByStudentAndGroup(student, group).isPresent();
    }

    @Override
    public void acceptRequestJoin(Long idGroup, String idUser,String bearerToken) {
        Student student = studentRequestMapper
                .DtoStudentToStudent(studentService.findById(idUser));
        GroupEntity group = groupMapper
                .groupDtoToGroupEntity(groupService.findById(idGroup));
        RequestJoinDto request = this.findByStudentAndGroup(student, group);
        RequestJoin wantedRequest = requestJoinMapper.requestJoinDtoToRequestJoin(request);
        wantedRequest.setAccepted(true);
        repository.save(wantedRequest);

        groupService.joinGroup(wantedRequest.getStudent().getId(), wantedRequest.getGroup().getId(), bearerToken);

        repository.delete(wantedRequest);
    }

    @Override
    public RequestJoinDto findByStudentAndGroup(Student student, GroupEntity group) {
        return repository.findByStudentAndGroup(student, group).map(requestJoinMapper::requestJoinToRequestJoinDto)
                .orElseThrow(() -> new RequestJoinNotFoundException("The request join was not found"));
    }

    @Override
    public void rejectRequestJoin(Long idGroup, String idStudent) {
        Student student = studentRequestMapper
                .DtoStudentToStudent(studentService.findById(idStudent));
        GroupEntity group = groupMapper
                .groupDtoToGroupEntity(groupService.findById(idGroup));
        Optional<RequestJoin> request = repository.findByStudentAndGroup(student, group);
        request.ifPresentOrElse(requestJoin -> repository.deleteById(requestJoin.getId()),
                () -> {
                    throw new RequestJoinNotFoundException("The request join you want to reject does not exist");

                });
    }

}
