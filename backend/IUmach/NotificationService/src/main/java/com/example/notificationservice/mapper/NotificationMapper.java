package com.example.notificationservice.mapper;

import com.example.notificationservice.dto.NotificationRequestDto;
import com.example.notificationservice.model.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface NotificationMapper {

    NotificationMapper INSTANCE= Mappers.getMapper(NotificationMapper.class);

    NotificationRequestDto notificationToDto(Notification notification);

    Notification requestDtoToNotification(NotificationRequestDto requestDto);
}
