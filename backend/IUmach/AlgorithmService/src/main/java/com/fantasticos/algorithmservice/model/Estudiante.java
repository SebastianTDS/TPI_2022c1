package com.fantasticos.algorithmservice.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.data.neo4j.core.schema.Relationship.Direction;

import com.fantasticos.algorithmservice.util.enums.Turno;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
@Node
public class Estudiante {

	@Id
	@GeneratedValue
	private Long id;
	@Property
	@NonNull
	private String parKey;
	@Property
	@NonNull
	private Long puntos;
	
	@Relationship(type = "ALUMNO_EN", direction = Direction.OUTGOING)
	private Facultad facultad;
	@Relationship(type = "ANOTADO_EN", direction = Direction.OUTGOING)
	private List<Carrera> carreras = new ArrayList<Carrera>();
	
	@Relationship(type = "CURSA_DIA", direction = Direction.OUTGOING)
	private List<Materia> materiasDia = new ArrayList<Materia>();
	@Relationship(type = "CURSA_TARDE", direction = Direction.OUTGOING)
	private List<Materia> materiasTarde = new ArrayList<Materia>();
	@Relationship(type = "CURSA_NOCHE", direction = Direction.OUTGOING)
	private List<Materia> materiasNoche = new ArrayList<Materia>();
	
	@Relationship(type = "INTERES_POR", direction = Direction.OUTGOING)
	private List<Tag> intereses = new ArrayList<Tag>();
	
	public void addCarrera(Carrera nueva) {
		if(!carreras.contains(nueva)) carreras.add(nueva);
	}
	
	public void removeCarrera(Carrera vieja) {
        carreras.remove(vieja);
	}
	
	public void addInteres(Tag nueva) {
		if(!intereses.contains(nueva)) intereses.add(nueva);
	}
	
	public void removeInteres(Tag vieja) {
        intereses.remove(vieja);
	}
	
	public void addMateria(Materia nueva, Turno turno) {
		switch(turno) {
		case DIA:
			if(!materiasDia.contains(nueva)) materiasDia.add(nueva);
			break;
		case TARDE:
			if(!materiasTarde.contains(nueva)) materiasTarde.add(nueva);
			break;
		case NOCHE:
			if(!materiasNoche.contains(nueva)) materiasNoche.add(nueva);
			break;
		}
	}
	
	public void removeMateria(Materia vieja) {
        materiasDia.remove(vieja);
        materiasTarde.remove(vieja);
        materiasNoche.remove(vieja);
	}
	
}
