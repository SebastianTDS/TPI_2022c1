package com.fantasticos.algorithmservice.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.neo4j.DataNeo4jTest;


@DataNeo4jTest
public class NodeCarreraTest {

	@Autowired
	private CarreraRepository repoCarrera;
	
	@Test
	public void queSePuedanBuscarTodasLasCarreras() {
		assertThat(repoCarrera.findAll().isEmpty()).isFalse();
	}
	
	@Test
	public void queSePuedaBuscarUnaCarrera() {
		String nombreCarrera = "Tecnicatura en desarrollo Móvil";
		
		assertThat(repoCarrera.findByName(nombreCarrera).isPresent()).isTrue();
	}
	
	@Test
	public void queSePuedaBuscarCarreraPorIdyFacultad() {
		Long idCarrera = 4L;
		Long idFacultad = 6L;
		
		assertThat(repoCarrera.findByFacultad(idCarrera, idFacultad).get()).isNotNull();
	}
	
	@Test
	public void queSePuedaListarCarrerasPorDepartamento() {
		Long departamento = 5L;
		String nombreCarrera = "Tecnicatura en desarrollo Móvil";
		String abrFacultad = "UNLAM";

		assertThat(repoCarrera.getCarrerasPorDepartamento(departamento, abrFacultad)
				.stream()
				.filter(materia -> materia.getName().equals(nombreCarrera))
				.findAny().isPresent()).isTrue();
	}
	
	@Test
	public void queSePuedaBuscarCarreraPorAlumno() {
		Long idCarrera = 3L;
		String idAlumno = "test1";
		
		assertThat(repoCarrera.findByAlumno(idCarrera, idAlumno).get()).isNotNull();
	}
}
