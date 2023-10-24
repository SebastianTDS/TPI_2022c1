package com.fantasticos.studentservice.dto.group;

import com.fantasticos.studentservice.dto.DtoStudent;
import lombok.Data;

import java.util.Set;

@Data
public class GroupDto {

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
    private Set<DtoStudent> students;
    private String idUserAdmin;

}
