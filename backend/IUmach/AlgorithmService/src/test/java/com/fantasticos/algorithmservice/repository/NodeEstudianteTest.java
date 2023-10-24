package com.fantasticos.algorithmservice.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.neo4j.DataNeo4jTest;

import com.fantasticos.algorithmservice.model.Carrera;
import com.fantasticos.algorithmservice.model.Estudiante;
import com.fantasticos.algorithmservice.model.Facultad;

@DataNeo4jTest
public class NodeEstudianteTest {

	@Autowired private EstudianteRepository repoEst;
	@Autowired private FacultadRepository repoFacu;
	@Autowired private CarreraRepository repoCarrera;
	
	@Test
	public void queSePuedaPersistirNuevoEstudiante() {
		Facultad buscada = repoFacu.findById(6L).orElse(null);
		
		assertThat(buscada).isNotNull();
		
		Estudiante cargado = repoEst.save(estudiante(buscada));
		
		assertThat(cargado).isNotNull();
		assertThat(cargado.getId()).isNotNull();
	}
	
	@Test
	public void queSePuedaGuardarCarreraDeEstudiante() {
		Facultad facultadBuscada = repoFacu.findById(6L).orElse(null);
		assertThat(facultadBuscada).isNotNull();
		
		Carrera carreraBuscada = repoCarrera.findByFacultad(4L, 6L).orElse(null);	
		assertThat(carreraBuscada).isNotNull();
		
		Estudiante estBuscado = repoEst.findByParKey("test1").orElse(null);
		assertThat(estBuscado).isNotNull();
		
		estBuscado.addCarrera(carreraBuscada);
		Estudiante cargado = repoEst.save(estBuscado);
		
		assertThat(cargado).isNotNull();
		assertThat(cargado.getCarreras()).isNotEmpty();
	}
	
	@Test
	public void queSePuedaVerSiElEstudianteEstaAsociadoAMaterias() {
		assertThat(repoEst.isCarreraSinMaterias("test1", 4L)).isTrue();
	}
	
	@Test
	public void queSePuedaBuscarEstudiantePorMateria() {
		String idEstudiante = "test2";
		Integer codigoMateria = 2619;
		
		assertThat(repoEst.isCursandoMateria(idEstudiante, codigoMateria)).isTrue();
	}
	
	private Estudiante estudiante(Facultad facultad) {
		Estudiante e1 = new Estudiante("test3", 0L);
		e1.setFacultad(facultad);
		return e1;
	}
	
	
}
