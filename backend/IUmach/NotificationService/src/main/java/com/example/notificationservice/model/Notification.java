package com.example.notificationservice.model;

import lombok.Data;
import com.example.notificationservice.util.Tipo;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String idStudentNotification;
    private String idStudent;
    private Long idGroup;
    private String nameNotificante;
    private String nameGroup;
    private Tipo tipoNotification;
    private Date dateAt=new Date();
    private Boolean view=false;
}
