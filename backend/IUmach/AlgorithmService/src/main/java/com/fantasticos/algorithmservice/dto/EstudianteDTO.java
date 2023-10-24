package com.fantasticos.algorithmservice.dto;

import com.fantasticos.algorithmservice.model.Estudiante;
import com.fantasticos.algorithmservice.util.enums.Turno;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class EstudianteDTO {

	private String id;
	private Long puntos;
	private Long idFacultad;
    private Long idCarrera;
    private Long idInteres;
    private Integer idMateria;
    private Turno turno;
	
	public Estudiante build() {
		return new Estudiante(this.id,this.puntos);
	}
	
	public EstudianteDTO changeId(String id) {
		this.id = id;
		return this;
	}
}
