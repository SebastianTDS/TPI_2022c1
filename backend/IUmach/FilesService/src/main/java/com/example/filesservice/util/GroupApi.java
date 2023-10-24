package com.example.filesservice.util;

import com.example.filesservice.dto.GroupResponseDto;
import com.example.filesservice.exception.GroupIdInvalidOrDoesNotExistException;
import org.springframework.http.RequestEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Optional;

public class GroupApi {

    public static Optional<GroupResponseDto> getGroupById(String token, Long id){
        try{
            RestTemplate restTemplate = new RestTemplate();
            RequestEntity<Void> request = RequestEntity.get(URI.create("http://localhost:8761/group/" + id))
                    .header("Authorization", token)
                    .build();
            return Optional.ofNullable(restTemplate.exchange(request, GroupResponseDto.class).getBody());
        }catch(Exception e){
            throw new GroupIdInvalidOrDoesNotExistException("The group id is invalid or does not exist");
        }
    }
}
