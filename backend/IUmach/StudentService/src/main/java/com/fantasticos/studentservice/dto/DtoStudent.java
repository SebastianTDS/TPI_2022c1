package com.fantasticos.studentservice.dto;

import lombok.*;

import java.util.Date;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class DtoStudent {
    private String id;
    private String firstName;
    private String lastName;
    private Date birthday;
    private Long valoration;
    private String email;
    private String phone;
    private Integer idFacultad;
}

