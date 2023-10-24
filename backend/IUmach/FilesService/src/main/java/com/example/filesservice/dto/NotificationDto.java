package com.example.filesservice.dto;

import com.example.filesservice.util.Tipo;
import lombok.Data;

@Data
public class NotificationDto {

    private String idStudentNotification;
    private String idStudent;
    private Long idGroup;
    private String nameNotificante;
    private String nameGroup;
    private Tipo tipoNotification;
}
