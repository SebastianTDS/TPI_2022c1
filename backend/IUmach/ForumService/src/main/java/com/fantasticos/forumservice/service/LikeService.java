package com.fantasticos.forumservice.service;

import com.fantasticos.forumservice.dto.LikeRequestDto;

public interface LikeService {
    void createLike(LikeRequestDto likeRequestDto, String bearerToken);

}
