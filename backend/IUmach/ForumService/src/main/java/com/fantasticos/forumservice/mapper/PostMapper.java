package com.fantasticos.forumservice.mapper;
import com.fantasticos.forumservice.dto.FilterDto;
import com.fantasticos.forumservice.dto.PostResponseDto;
import com.fantasticos.forumservice.dto.PostRequestDto;
import com.fantasticos.forumservice.model.Post;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PostMapper {
    PostMapper INSTANCE= Mappers.getMapper(PostMapper.class);

    PostResponseDto postToDto(Post post);

    Post requestDtoToPost(PostRequestDto postRequestDto);

}
