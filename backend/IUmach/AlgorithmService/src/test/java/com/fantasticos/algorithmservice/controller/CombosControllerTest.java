package com.fantasticos.algorithmservice.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fantasticos.algorithmservice.model.Carrera;
import com.fantasticos.algorithmservice.model.Departamento;
import com.fantasticos.algorithmservice.model.Facultad;
import com.fantasticos.algorithmservice.model.Materia;
import com.fantasticos.algorithmservice.model.Tag;
import com.fantasticos.algorithmservice.service.CombosService;

@WebMvcTest(CombosController.class)
public class CombosControllerTest {

	@Autowired
	private MockMvc mock;

	@MockBean
	private CombosService service;

	@Test
	public void queSePuedanSolicitarLasFacultades() throws Exception {
		when(service.getFacultades()).thenReturn(facultades());

		this.mock.perform(get("/algorithm/graph/facultades")).andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.*", Matchers.isA(ArrayList.class)))
				.andExpect(jsonPath("$[0].abr", Matchers.is("UNLAM")));
	}

	@Test
	public void queSePuedanSolicitarDepartamentosDeFacultad() throws Exception {
		String abrFacultad = "UNLAM";

		when(service.getDepartamentos(abrFacultad)).thenReturn(departamentos());

		this.mock.perform(get("/algorithm/graph/" + abrFacultad + "/departamentos")).andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.*", Matchers.isA(ArrayList.class)))
				.andExpect(jsonPath("$[0].name", Matchers.is("Ingeniería")));
	}

	@Test
	public void queSePuedanSolicitarCarrerasDeDepartamento() throws Exception {
		String abrFacultad = "UNLAM";
		Long departamento = 0L;

		when(service.getCarreras(abrFacultad, departamento)).thenReturn(carreras());

		this.mock.perform(get("/algorithm/graph/" + abrFacultad + "/" + departamento + "/carreras"))
				.andDo(print())
				.andExpect(status().isOk()).andExpect(jsonPath("$.*", Matchers.isA(ArrayList.class)))
				.andExpect(jsonPath("$[0].name", Matchers.is("Tecnicatura en desarrollo Móvil")));
	}

	@Test
	public void queSePuedanSolicitarMateriasDeCarrera() throws Exception {
		String abrFacultad = "UNLAM";
		Long departamento = 0L;
		Long carrera = 0L;

		when(service.getMaterias(abrFacultad, carrera)).thenReturn(materias());

		this.mock.perform(get("/algorithm/graph/" + abrFacultad 
							+ "/" + departamento
							+ "/" + carrera
							+ "/materias"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.*", Matchers.isA(ArrayList.class)))
				.andExpect(jsonPath("$[0].title", Matchers.is("Programación Móvil I")));
	}
	
	@Test
	public void queSePuedanListarLosTags() throws Exception {
		when(service.getTags()).thenReturn(tags());

		this.mock.perform(get("/algorithm/graph/tags")).andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.*", Matchers.isA(ArrayList.class)))
				.andExpect(jsonPath("$[0].name", Matchers.is("WebDev")));
	}

	private List<Tag> tags() {
		Tag t1 = new Tag(0L, "WebDev");
		Tag t2 = new Tag(1L, "C++");
		Tag t3 = new Tag(2L, "PHP");
		
		return Arrays.asList(t1,t2,t3);
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

		return Arrays.asList(c1, c2);
	}

	private List<Departamento> departamentos() {
		Departamento d1 = new Departamento(0L, "Ingeniería");
		Departamento d2 = new Departamento(1L, "Económicas");
		Departamento d3 = new Departamento(2L, "Derecho");

		return Arrays.asList(d1, d2, d3);
	}

	private List<Facultad> facultades() {
		Facultad f1 = new Facultad(0L, "UNLAM", "Universidad Nacional de la Matanza");
		Facultad f2 = new Facultad(0L, "UBA", "Universidad de Buenos Aires");
		Facultad f3 = new Facultad(0L, "UTN", "Universidad Tecnica Nacional");

		return Arrays.asList(f1, f2, f3);
	}

}
