package com.fantasticos.studentservice.mapper;

import com.fantasticos.studentservice.dto.request.RequestJoinDataDto;
import com.fantasticos.studentservice.dto.request.RequestJoinDto;
import com.fantasticos.studentservice.model.RequestJoin;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RequestJoinMapper {

    RequestJoinMapper INSTANCE = Mappers.getMapper(RequestJoinMapper.class);

    RequestJoinDto requestJoinToRequestJoinDto(RequestJoin requestJoin);

    RequestJoin requestJoinDtoToRequestJoin(RequestJoinDto requestJoinDto);

    List<RequestJoinDto> requestJoinListToRequestJoinDtoList(List<RequestJoin> requestJoinList);

    RequestJoin requestJoinCreateDtoToRequestJoin(RequestJoinDataDto requestJoinDataDto);

    RequestJoin merge(@MappingTarget RequestJoin requestTarget, RequestJoinDataDto requestSource);
}
