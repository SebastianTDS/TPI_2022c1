package com.fantasticos.studentservice.service;

import com.fantasticos.studentservice.dto.*;
import com.fantasticos.studentservice.dto.group.GroupCreateDto;
import com.fantasticos.studentservice.dto.group.GroupDto;
import com.fantasticos.studentservice.dto.group.GroupUpdateDto;
import com.fantasticos.studentservice.dto.group.RecommendedGroupDto;
import com.fantasticos.studentservice.dto.group.RecommendedStudentDto;

import java.util.List;

public interface GroupService {

    GroupDto findById(Long id);

    List<GroupDto> findAll();

    void save(GroupCreateDto groupToCreate, String userName, String bearerToken);

    void update(Long id, GroupUpdateDto groupToUpdate);

    void delete(Long id, String bearerToken, String idUser);

    void joinGroup(String idUser, Long idGroup, String bearerToken);

    List<GroupDto> findMyGroups(String idUser);

    List<RecommendedGroupDto> findRecommendedGroups(FilterDto search, String idUser);

    void leaveGroup(String idUser, Long idGroup, String bearerToken);

    List<RecommendedStudentDto> findRecommendedStudents(FilterDto search, String idUser);

}
