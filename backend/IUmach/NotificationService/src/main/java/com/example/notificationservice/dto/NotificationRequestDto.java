package com.example.notificationservice.dto;

import com.example.notificationservice.util.Tipo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationRequestDto {
    private Long id;
    @NotBlank
    @NotEmpty
    @NotNull
    private String idStudentNotification;
    @NotBlank
    @NotEmpty
    @NotNull
    private String idStudent;
    @NotNull
    private Long idGroup;
    @NotBlank
    @NotEmpty
    @NotNull
    private String nameNotificante;
    @NotBlank
    @NotEmpty
    @NotNull
    private String nameGroup;
    @NotNull
    private Tipo tipoNotification;
    private Date dateAt;
    private Boolean view;
}
