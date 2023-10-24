package com.fantasticos.studentservice.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class FilterDto {

    private Integer materia;
    private Long tag;
    private Integer pagina;
    private String user;
	private Long group;
}
