package com.fantasticos.forumservice.util;

import com.fantasticos.forumservice.dto.DtoUser;
import com.fantasticos.forumservice.dto.StudentRequestDto;
import com.fantasticos.forumservice.exception.GroupIdInvalidOrDoesNotExistException;
import com.fantasticos.forumservice.exception.StudentDoestNotExistsOrWasEliminatedException;
import org.springframework.http.RequestEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Optional;

public class StudentMethods {

    public static Optional<DtoUser> getMyId(String token) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            RequestEntity<Void> request = RequestEntity.get(URI.create("http://localhost:8761/student/my-id"))
                    .header("Authorization", token)
                    .build();
            return Optional.ofNullable(restTemplate.exchange(request, DtoUser.class).getBody());
        } catch (Exception e) {
            throw new StudentDoestNotExistsOrWasEliminatedException("The student does not exist or was eliminated");
        }
    }

    public static Optional<StudentRequestDto> getMyUser(String token) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            RequestEntity<Void> request = RequestEntity.get(URI.create("http://localhost:8761/student/get-user"))
                    .header("Authorization", token)
                    .build();
            return Optional.ofNullable(restTemplate.exchange(request, StudentRequestDto.class).getBody());
        } catch (Exception e) {
            throw new StudentDoestNotExistsOrWasEliminatedException("The student does not exist or was eliminated");
        }
    }

    public static Optional<StudentRequestDto> getUserById(String token, String id) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            RequestEntity<Void> request = RequestEntity.post(URI.create("http://localhost:8761/student/get-user/" + id))
                    .header("Authorization", token)
                    .build();
            return Optional.ofNullable(restTemplate.exchange(request, StudentRequestDto.class).getBody());
        } catch (Exception e) {
            throw new StudentDoestNotExistsOrWasEliminatedException("The student does not exist or was eliminated");
        }
    }
}

