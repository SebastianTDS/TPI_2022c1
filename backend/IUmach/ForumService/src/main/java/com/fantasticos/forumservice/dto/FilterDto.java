package com.fantasticos.forumservice.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class FilterDto {

    private Long idUniversity;

    private Long idDepartment;

    private Long idCareer;

    private Long idSubject;

    private Long idTag;

    private String name;


}
