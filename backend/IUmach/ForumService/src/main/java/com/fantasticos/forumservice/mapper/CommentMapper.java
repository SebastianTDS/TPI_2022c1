package com.fantasticos.forumservice.mapper;

import com.fantasticos.forumservice.dto.CommentRequestDto;
import com.fantasticos.forumservice.model.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    Comment RequestDtoToComment(CommentRequestDto commentRequestDto);

}
