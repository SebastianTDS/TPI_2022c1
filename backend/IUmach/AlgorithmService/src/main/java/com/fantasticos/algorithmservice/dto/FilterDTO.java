package com.fantasticos.algorithmservice.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class FilterDTO {

	private Integer materia;
	private Long tag;
	@Min(value = 0, message = "Indique la página")
	@NotNull(message= "Indique la página")
	private Integer pagina;
	private String user;
	private Long group;
	
	
}
