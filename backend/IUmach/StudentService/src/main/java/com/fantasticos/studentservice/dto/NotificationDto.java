package com.fantasticos.studentservice.dto;

import com.fantasticos.studentservice.util.Tipo;
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
