package com.fantasticos.studentservice.service;

import com.fantasticos.studentservice.dto.request.RequestJoinDataDto;
import com.fantasticos.studentservice.dto.request.RequestJoinDto;
import com.fantasticos.studentservice.model.GroupEntity;
import com.fantasticos.studentservice.model.Student;

import java.util.List;

public interface RequestJoinService {

    RequestJoinDto findById(Long id);

    List<RequestJoinDto> findAll();

    void save(RequestJoinDataDto requestJoinDataDto, String userName, String bearerToken);
    
    void invite(RequestJoinDataDto requestJoinDataDto, String userName, String bearerToken);
    
    void inviteEmail(RequestJoinDataDto requestJoinDataDto, String userName, String bearerToken);

    void acceptRequestJoin(Long idGroup,String idUser, String bearerToken);

    void rejectRequestJoin(Long idGroup,String idUser);

    RequestJoinDto findByStudentAndGroup(Student student, GroupEntity group);
}
