package com.example.notificationservice.util;

import com.example.notificationservice.dto.DtoUser;
import com.example.notificationservice.dto.StudentRequestDto;
import com.example.notificationservice.exeptions.StudentDoestNotExistsOrWasEliminatedException;
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

