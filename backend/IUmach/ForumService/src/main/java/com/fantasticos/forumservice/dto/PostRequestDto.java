package com.fantasticos.forumservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Date;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PostRequestDto {
    @NotBlank
    @NotEmpty
    private String name;
    @NotBlank
    @NotEmpty
    private String content;

    private Long idGroup;

    private Long idUniversity;

    private Long idDepartment;

    private Long idCareer;

    private Long idSubject;

    private Long idTag;

    private String tagName;

}
