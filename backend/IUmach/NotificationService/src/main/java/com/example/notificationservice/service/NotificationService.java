package com.example.notificationservice.service;

import com.example.notificationservice.dto.NotificationRequestDto;
import com.example.notificationservice.model.Notification;

import javax.validation.Valid;
import java.util.List;

public interface NotificationService {

    List<Notification> getNotification(String token);

    Notification createNotification(@Valid NotificationRequestDto requestDto, String token);

    void deleteNotification(Long idNotification, String token);
}
