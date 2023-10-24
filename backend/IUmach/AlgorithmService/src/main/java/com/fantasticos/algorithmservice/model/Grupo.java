package com.fantasticos.algorithmservice.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.data.neo4j.core.schema.Relationship.Direction;

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
public class Grupo {

	@Id
	@GeneratedValue
	private Long id;
	@Property
	@NonNull
	private Long parKey;
	
	@Relationship(type = "ADMINISTRA", direction = Direction.INCOMING)
	private Estudiante administrador;
	@Relationship(type = "INTEGRA", direction = Direction.INCOMING)
	private List<Estudiante> miembros = new ArrayList<Estudiante>();
	
	@Relationship(type = "SOBRE", direction = Direction.OUTGOING)
	private Materia materia;
	@Relationship(type = "LES_INTERESA", direction = Direction.OUTGOING)
	private List<Tag> intereses = new ArrayList<Tag>();
	
	public void addMiembro(Estudiante nuevo) {
		if(!miembros.contains(nuevo)) miembros.add(nuevo);
	}
	
	public void removeMiembro(Estudiante viejo) {
        miembros.remove(viejo);
	}
}
