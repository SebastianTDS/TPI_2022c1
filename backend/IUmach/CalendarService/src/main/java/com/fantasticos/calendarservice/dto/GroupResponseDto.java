package com.fantasticos.calendarservice.dto;

import lombok.Data;

import java.util.Set;

@Data
public class GroupResponseDto {
    private Long id;
    private String name;
    private String image;
    private String description;
    private Boolean closed;
    private Integer maxNumberOfStudents;
    private Integer numberOfStudents;
    private String shift;
    private String career;
    private String subject;
    private Integer assessment;
    private Set<StudentResponseDto> students;

}
