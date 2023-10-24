package com.fantasticos.algorithmservice.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.neo4j.DataNeo4jTest;

import com.fantasticos.algorithmservice.util.enums.Turno;

@DataNeo4jTest
public class NodeMateriaTest {

	@Autowired
	private MateriaRepository repoMateria;
	
	@Test
	public void queSePuedanListarMaterias() {
		assertThat(repoMateria.findAll()).isNotEmpty();
	}
	
	@Test
	public void queSePuedaBuscarMateriaPorTitulo() {
		assertThat(repoMateria.findByTitle("Inglés Técnico I")).isPresent();
	}

	@Test
	public void queSePuedanBuscarLasMateriasPorCarrera() {
		Long idCarrera = 4L;
		String nombreMateria = "Programación Móvil I";
		String abrFacultad = "UNLAM";
		
		assertThat(repoMateria.getMateriasPorCarrera(idCarrera, abrFacultad)
				.stream()
				.filter(materia -> materia.getTitle().equals(nombreMateria))
				.findAny()).isPresent();
	}
	
	@Test
	public void queSePuedaBuscarMateriaPorCarrera() {
		Integer codigoMateria = 2619;
		Long idCarrera = 3L;
		
		assertThat(repoMateria.findByCarrera(codigoMateria, idCarrera)).isNotEmpty();
	}
	
	@Test
	public void queSePuedaCheckearTurnoDisponible() {
		Integer codigoMateria = 2619;
		Turno turno = Turno.DIA;
		
		assertThat(repoMateria.isTurnoDisponible(codigoMateria, turno.getNodo())).isTrue();
	}
	
	@Test
	public void queSePuedaCheckearTurnoNoDisponible() {
		Integer codigoMateria = 2619;
		Turno turno = Turno.TARDE;
		
		assertThat(repoMateria.isTurnoDisponible(codigoMateria, turno.getNodo())).isFalse();
	}
	
	@Test
	public void queSePuedaBuscarMateriaPorCodigo() {
		Integer codigoMateria = 2619;
		
		assertThat(repoMateria.findByCodigo(codigoMateria)).isNotEmpty();
	}
	
	@Test
	public void queSePuedanListarTurnos() {
		Integer codigoMateria = 2619;
		String abrFacultad = "UNLAM";
		
		assertThat(repoMateria.findTurnos(abrFacultad, codigoMateria)).isNotEmpty();
	}
	
}
