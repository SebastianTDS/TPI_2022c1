package com.example.notificationservice.util;

import com.example.notificationservice.dto.GroupResponseDto;
import com.example.notificationservice.exeptions.GroupIdInvalidOrDoesNotExistException;
import org.springframework.http.RequestEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Optional;

public class GroupMethods {

    public static Optional<GroupResponseDto> getGroupById(String token, Long id){
        try {
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
