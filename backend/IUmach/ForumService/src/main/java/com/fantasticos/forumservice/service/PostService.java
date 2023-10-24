package com.fantasticos.forumservice.service;

import com.fantasticos.forumservice.dto.*;
import com.fantasticos.forumservice.model.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.logging.Filter;

public interface PostService {

    List<PostResponseDto> findAllGroupPost(String token, Long idGroup, Pageable page,FilterDto params);

    List<PostResponseDto> findAllGeneralPost(String token,FilterDto params, Pageable page);

    Post createPost(PostRequestDto postDto,String token);



}
