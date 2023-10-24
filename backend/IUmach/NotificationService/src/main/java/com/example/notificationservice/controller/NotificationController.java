package com.example.notificationservice.controller;

import com.example.notificationservice.dto.NotificationDeleteRequestDto;
import com.example.notificationservice.dto.NotificationRequestDto;
import com.example.notificationservice.model.Notification;
import com.example.notificationservice.service.NotificationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/notification")
@AllArgsConstructor
public class NotificationController {

    private NotificationService notificationService;

    @GetMapping("/version")
    public ResponseEntity<String> version() {
        return ResponseEntity.ok("Hola NotificationController Version");
    }

    @GetMapping("/get-notification")
    public ResponseEntity<List<Notification>> getNotification(@RequestHeader("Authorization") String bearerToken) {
        return new ResponseEntity<>(notificationService.getNotification(bearerToken), HttpStatus.OK);
    }

    @PostMapping("/create-notification")
    public ResponseEntity<Notification> createNotification(@RequestHeader("Authorization") String bearerToken, @Valid @RequestBody NotificationRequestDto requestDto) {
        return new ResponseEntity<>(notificationService.createNotification(requestDto,bearerToken), HttpStatus.OK);
    }

    @DeleteMapping ("/delete-notification")
    @ResponseStatus(HttpStatus.OK)
    public void deleteNotification(@RequestHeader("Authorization") String bearerToken,@Valid @RequestBody NotificationDeleteRequestDto requestDto) {
        notificationService.deleteNotification(requestDto.getId(),bearerToken);
    }
}
