package com.fantasticos.algorithmservice.dto;

import java.util.List;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class EstudianteCoincidenciaDTO {

	private String idEstudiante;
	private List<String> tags;
	private List<String> materias;
	private List<String> carreras;
	private String facultad;
	private Double coincidencia;
	
}
