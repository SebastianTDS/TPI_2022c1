package com.example.filesservice.util;

import com.example.filesservice.dto.StudentRequestDto;
import com.example.filesservice.exception.StudentDoestNotExistsOrWasEliminatedException;
import org.springframework.http.RequestEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Optional;

public class StudentApi {

    public static Optional<StudentRequestDto> getUserById(String token, String id) throws StudentDoestNotExistsOrWasEliminatedException {
        try{
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
