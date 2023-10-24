package com.fantasticos.algorithmservice.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.fantasticos.algorithmservice.model.Carrera;
import com.fantasticos.algorithmservice.model.Departamento;
import com.fantasticos.algorithmservice.model.Facultad;
import com.fantasticos.algorithmservice.model.Materia;
import com.fantasticos.algorithmservice.repository.CarreraRepository;
import com.fantasticos.algorithmservice.repository.DepartamentoRepository;
import com.fantasticos.algorithmservice.repository.FacultadRepository;
import com.fantasticos.algorithmservice.repository.MateriaRepository;

@SpringBootTest
public class CombosServiceTest {

	@Autowired private CombosService service;
	
	@MockBean private FacultadRepository repoFacu;
	@MockBean private DepartamentoRepository repoDepto;
	@MockBean private CarreraRepository repoCarrera;
	@MockBean private MateriaRepository repoMateria;
	
	@Test
	public void queSeListenFacultades() {
		when(repoFacu.findAll()).thenReturn(facultades());
		
		assertThat(service.getFacultades()).containsAll(facultades());
	}
	
	@Test
	public void queSeListenDepartamentos() {
		String abrFacultad = "UNLAM";
		
		when(repoDepto.findByFacultad(abrFacultad)).thenReturn(departamentos());
		
		assertThat(service.getDepartamentos(abrFacultad)).containsAll(departamentos());
	}
	
	@Test
	public void queSeListenCarreras() {
		String abrFacultad = "UNLAM";
		Long departamento = 0L;
		
		when(repoCarrera.getCarrerasPorDepartamento(departamento,abrFacultad)).thenReturn(carreras());
		
		assertThat(service.getCarreras(abrFacultad, departamento)).containsAll(carreras());
	}
	
	@Test
	public void queSeListenMaterias() {
		String abrFacultad = "UNLAM";
		Long carrera = 0L;
		
		when(repoMateria.getMateriasPorCarrera(carrera, abrFacultad)).thenReturn(materias());
		
		assertThat(service.getMaterias(abrFacultad, carrera)).containsAll(materias());
	}
	
	private List<Materia> materias() {
		Materia m1 = new Materia(0L, 3001, "Programación Móvil I");
		Materia m2 = new Materia(1L, 2981, "Matemática General I");
		Materia m3 = new Materia(2L, 2982, "Inglés Técnico I");

		return Arrays.asList(m1, m2, m3);
	}

	private List<Carrera> carreras() {
		Carrera c1 = new Carrera(0L, "Tecnicatura en desarrollo Móvil");
		Carrera c2 = new Carrera(1L, "Tecnicatura en Desarrollo WEB");
		
		return Arrays.asList(c1,c2);
	}

	private List<Departamento> departamentos() {
		Departamento d1 = new Departamento(0L, "Ingeniería");
		Departamento d2 = new Departamento(1L, "Económicas");
		Departamento d3 = new Departamento(2L, "Derecho");
		
		return Arrays.asList(d1,d2,d3);
	}

	private List<Facultad> facultades() {
		Facultad f1 = new Facultad(0L,"UNLAM","Universidad Nacional de la Matanza");
		Facultad f2 = new Facultad(1L,"UBA","Universidad de Buenos Aires");
		Facultad f3 = new Facultad(2L,"UTN","Universidad Tecnica Nacional");
		
		return Arrays.asList(f1,f2,f3);
	}
	
}
