package com.fantasticos.studentservice.dto.group;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class GroupCreateDto {

    @NotNull(message = "Name cannot be null")
    @Size(min = 5, max = 40, message = "The name should be between 5 and 40 characters")
    private String name;
    private String image;
    @NotNull(message = "Description cannot be null")
    @Size(min = 10, max = 80, message = "The description should be between 10 and 80 characters")
    private String description;
    private Boolean closed;
    @Max(value = 8, message = "The maximum number of students is 8")
    private Integer maxNumberOfStudents;
    private String shift;
    private String career;
    private String subject;
    private Integer idSubject;
}
