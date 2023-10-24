package com.fantasticos.studentservice.dto.group;

import java.util.List;

import lombok.Data;

@Data
public class EstudianteCoincidenciaDTO{
	private String idEstudiante;
	private List<String> tags;
	private List<String> materias;
	private List<String> carreras;
	private String facultad;
	private Double coincidencia;
}
