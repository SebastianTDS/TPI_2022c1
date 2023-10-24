package com.fantasticos.studentservice.dto.group;

import com.fantasticos.studentservice.dto.DtoStudent;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class RecommendedGroupDto {

    private Long idGrupo;
    private List<String> tags;
    private Integer codigoMateria;
    private String nameMateria;
    private Double coincidencia;
    private String name;
    private String image;
    private String description;
    private Boolean closed;
    private Integer maxNumberOfStudents;
    private Integer numberOfStudents;
    private String shift;
    private String career;
    private String subject;
    private Set<DtoStudent> students;

}
