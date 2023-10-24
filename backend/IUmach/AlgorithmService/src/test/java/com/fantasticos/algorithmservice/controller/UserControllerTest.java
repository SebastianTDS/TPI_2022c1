package com.fantasticos.algorithmservice.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fantasticos.algorithmservice.dto.DtoUser;
import com.fantasticos.algorithmservice.dto.EstudianteDTO;
import com.fantasticos.algorithmservice.model.Estudiante;
import com.fantasticos.algorithmservice.model.Facultad;
import com.fantasticos.algorithmservice.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(UserController.class)
public class UserControllerTest {

	@Autowired
	private MockMvc mock;
	
	@MockBean
	private UserService service;

	
	@Test
	public void queSePuedaCargarNuevoEstudiante() throws Exception{
		EstudianteDTO est = modeloEstudiante();
		
		when(service.cargarEstudiante(est)).thenReturn(estudiante());
		
		this.mock.perform(post("/algorithm/user/cargar")
				.content(asJsonString(est))
				.requestAttr("UserID", new DtoUser(est.getId()))
				.contentType(MediaType.APPLICATION_JSON)
			    .accept(MediaType.APPLICATION_JSON))
				.andDo(print())
		 		.andExpect(status().isCreated())
		 		.andExpect(jsonPath("$.id", Matchers.notNullValue()));
	}
	
	@Test
	public void queSePuedaAnotarEstudianteEnCarrera() throws Exception {
		EstudianteDTO est = modeloEstudiante();
		
		when(service.anotarCarrera(est)).thenReturn(estudiante());
		
		this.mock.perform(put("/algorithm/user/anotar-carrera")
				.content(asJsonString(est))
				.requestAttr("UserID", new DtoUser(est.getId()))
				.contentType(MediaType.APPLICATION_JSON)
			    .accept(MediaType.APPLICATION_JSON))
				.andDo(print())
		 		.andExpect(status().isOk())
		 		.andExpect(jsonPath("$.id", Matchers.notNullValue()));
	}
	
	@Test
	public void queSePuedaAbandonarCarrera() throws Exception {
		EstudianteDTO est = modeloEstudiante();
		
		when(service.abandonarCarrera(est)).thenReturn(estudiante());
		
		this.mock.perform(delete("/algorithm/user/abandonar-carrera")
				.content(asJsonString(est))
				.requestAttr("UserID", new DtoUser(est.getId()))
				.contentType(MediaType.APPLICATION_JSON)
			    .accept(MediaType.APPLICATION_JSON))
				.andDo(print())
		 		.andExpect(status().isOk())
		 		.andExpect(jsonPath("$.id", Matchers.notNullValue()));
	}

	@Test
	public void queSePuedaAnotarEstudianteEnMateria() throws Exception {
		EstudianteDTO est = modeloEstudiante();
		
		when(service.anotarMateria(est)).thenReturn(estudiante());
		
		this.mock.perform(put("/algorithm/user/anotar-materia")
				.content(asJsonString(est))
				.requestAttr("UserID", new DtoUser(est.getId()))
				.contentType(MediaType.APPLICATION_JSON)
			    .accept(MediaType.APPLICATION_JSON))
				.andDo(print())
		 		.andExpect(status().isOk())
		 		.andExpect(jsonPath("$.id", Matchers.notNullValue()));
	}
	
	@Test
	public void queSePuedaSumarPuntosAEstudiantes() throws Exception {
		EstudianteDTO est = modeloEstudiante();
		
		when(service.sumarPuntos(est)).thenReturn(estudiante());
		
		this.mock.perform(put("/algorithm/user/sumar-puntos")
				.content(asJsonString(est))
				.requestAttr("UserID", new DtoUser(est.getId()))
				.contentType(MediaType.APPLICATION_JSON)
			    .accept(MediaType.APPLICATION_JSON))
				.andDo(print())
		 		.andExpect(status().isOk())
		 		.andExpect(jsonPath("$.id", Matchers.notNullValue()));
	}
	
	@Test
	public void queSePuedaAbandonarMateria() throws Exception {
		EstudianteDTO est = modeloEstudiante();
		
		when(service.abandonarMateria(est)).thenReturn(estudiante());
		
		this.mock.perform(delete("/algorithm/user/abandonar-materia")
				.content(asJsonString(est))
				.requestAttr("UserID", new DtoUser(est.getId()))
				.contentType(MediaType.APPLICATION_JSON)
			    .accept(MediaType.APPLICATION_JSON))
				.andDo(print())
		 		.andExpect(status().isOk())
		 		.andExpect(jsonPath("$.id", Matchers.notNullValue()));
	}
	
	@Test
	public void queSePuedaVerInfoDeUsuario() throws Exception {
		String userId = "aaa";
		
		when(service.verEstudiante(userId)).thenReturn(estudiante());
		
		this.mock.perform(get("/algorithm/user/" + userId))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", Matchers.notNullValue()))
				.andExpect(jsonPath("$.facultad.abr", Matchers.is("UNLAM")));
	}
	
	@Test
	public void queSePuedaAgregarInteresAUsuario() throws Exception{
		EstudianteDTO est = modeloEstudiante();
		
		when(service.cargarInteres(est)).thenReturn(estudiante());
		
		this.mock.perform(put("/algorithm/user/cargar-interes")
				.content(asJsonString(est))
				.requestAttr("UserID", new DtoUser(est.getId()))
				.contentType(MediaType.APPLICATION_JSON)
			    .accept(MediaType.APPLICATION_JSON))
				.andDo(print())
		 		.andExpect(status().isOk())
		 		.andExpect(jsonPath("$.id", Matchers.notNullValue()));
	}
	
	@Test
	public void queSePuedaBorrarInteresAUsuario() throws Exception{
		EstudianteDTO est = modeloEstudiante();
		
		when(service.borrarInteres(est)).thenReturn(estudiante());
		
		this.mock.perform(delete("/algorithm/user/borrar-interes")
				.content(asJsonString(est))
				.requestAttr("UserID", new DtoUser(est.getId()))
				.contentType(MediaType.APPLICATION_JSON)
			    .accept(MediaType.APPLICATION_JSON))
				.andDo(print())
		 		.andExpect(status().isOk())
		 		.andExpect(jsonPath("$.id", Matchers.notNullValue()));
	}
	
	private Estudiante estudiante() {
		Estudiante e1 = new Estudiante("aaa", 0L);
		e1.setFacultad(new Facultad(0L, "UNLAM", "Universidad Nacional de la Matanza"));
		e1.setId(10L);
		return e1;
	}
	
	private EstudianteDTO modeloEstudiante() {
		EstudianteDTO dto = new EstudianteDTO();
		
		dto.setId("aaa");
		dto.setPuntos(0L);
		dto.setIdFacultad(6L);
		dto.setIdCarrera(500l);
		dto.setIdInteres(44L);
		
		return dto;
	}
	
	public static String asJsonString(final Object obj) {
	    try {
	        return new ObjectMapper().writeValueAsString(obj);
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}
}
