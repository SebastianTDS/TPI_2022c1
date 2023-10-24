package com.fantasticos.calendarservice.util;

import com.fantasticos.calendarservice.dto.DtoStudent;
import com.fantasticos.calendarservice.dto.GroupResponseDto;
import com.fantasticos.calendarservice.dto.NotificationDto;
import com.fantasticos.calendarservice.dto.StudentResponseDto;
import org.springframework.http.RequestEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Optional;

public class Apis {

    public static Optional<GroupResponseDto> getGroupById(String token, Long id){
        try{
            RestTemplate restTemplate = new RestTemplate();
            RequestEntity<Void> request = RequestEntity.get(URI.create("http://localhost:8761/group/" + id))
                    .header("Authorization", token)
                    .build();
            return Optional.ofNullable(restTemplate.exchange(request, GroupResponseDto.class).getBody());
        }catch(Exception e){
            throw new IllegalStateException("The group id is invalid or does not exist");
        }
    }

    public static Optional<DtoStudent> getUser(String token){
        try{
            RestTemplate restTemplate = new RestTemplate();
            RequestEntity<Void> request = RequestEntity.get(URI.create("http://localhost:8761/student/my-id/"))
                    .header("Authorization", token)
                    .build();
            return Optional.ofNullable(restTemplate.exchange(request, DtoStudent.class).getBody());
        } catch (Exception e) {
            throw new IllegalStateException("The student does not exist or was eliminated");
        }
    }

    public static Optional<StudentResponseDto> getUserById(String token, String id){
        try{
            RestTemplate restTemplate = new RestTemplate();
            RequestEntity<Void> request = RequestEntity.post(URI.create("http://localhost:8761/student/get-user/" + id))
                    .header("Authorization", token)
                    .build();
            return Optional.ofNullable(restTemplate.exchange(request, StudentResponseDto.class).getBody());
        } catch (Exception e) {
            throw new IllegalStateException("The student does not exist or was eliminated");
        }
    }

    public static void createNotification(String bearerToken, NotificationDto notificationDto) {

        RestTemplate restTemplate = new RestTemplate();
        RequestEntity<NotificationDto> request = RequestEntity.post(URI.create("http://localhost:8761/notification/create-notification"))
                .header("Authorization", bearerToken)
                .body(notificationDto);
        restTemplate.exchange(request, NotificationDto.class);
    }

}
