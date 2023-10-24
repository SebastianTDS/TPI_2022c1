package com.fantasticos.forumservice.dto;

import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class StudentRequestDto {
    private String id;
    private String firstName;
    private String lastName;
    private Date birthday;
    private Long valoration;
    private String email;
    private String phone;
}


