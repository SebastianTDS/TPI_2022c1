package com.fantasticos.studentservice.dto;

import lombok.*;

import java.util.Date;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class DtoRequestStudent {
    private String id;
    private String firstName;
    private String lastName;
    private Date birthday;
    private Long valoration;
    private String email;
    private String phone;
    private Long responsable;
    private Long colaborador;
    private Long proActivo;
    private Long organizado;
    private Long conocedor;
    private Long contribuciones;
    private Long detallista;
}
