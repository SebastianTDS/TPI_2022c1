package com.fantasticos.forumservice.service;

import com.fantasticos.forumservice.dto.CommentRequestDto;

public interface CommentService {

    void createComment(CommentRequestDto commentRequestDto, String bearerToken);

}
