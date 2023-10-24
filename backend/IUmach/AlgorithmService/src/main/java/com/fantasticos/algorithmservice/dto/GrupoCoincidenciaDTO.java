package com.fantasticos.algorithmservice.dto;

import java.util.List;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class GrupoCoincidenciaDTO {

	private Long idGrupo;
	private List<String> tags;
	private Integer codigoMateria;
	private String nameMateria;
	private Double coincidencia;
	
}
