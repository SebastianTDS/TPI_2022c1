package com.fantasticos.studentservice.mapper;

import com.fantasticos.studentservice.dto.group.GroupCreateDto;
import com.fantasticos.studentservice.dto.group.GroupDto;
import com.fantasticos.studentservice.dto.group.GroupUpdateDto;
import com.fantasticos.studentservice.model.GroupEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;


@Mapper(componentModel = "spring")
public interface GroupMapper {

    GroupMapper INSTANCE = Mappers.getMapper(GroupMapper.class);

    GroupDto groupToGroupDto(GroupEntity group);

    GroupEntity groupCreateDtoToGroupEntity(GroupCreateDto groupCreateDto);

    GroupEntity groupDtoToGroupEntity(GroupDto groupDto);

    GroupEntity merge(@MappingTarget GroupEntity groupTarget, GroupUpdateDto groupSource);

    List<GroupDto> groupListToGroupListDto(List<GroupEntity> groupList);

    List<GroupDto> toGroupListDto(Set<GroupEntity> groupList);
}
