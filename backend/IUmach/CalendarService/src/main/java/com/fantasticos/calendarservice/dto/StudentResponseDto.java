package com.fantasticos.calendarservice.dto;

import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class StudentResponseDto {
    private String id;
    private String firstName;
    private String lastName;
    private Date birthday;
    private Long valoration;
    private String email;
    private String phone;
}


