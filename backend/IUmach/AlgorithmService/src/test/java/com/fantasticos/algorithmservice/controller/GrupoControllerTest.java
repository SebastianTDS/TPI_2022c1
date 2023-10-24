package com.fantasticos.algorithmservice.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fantasticos.algorithmservice.dto.DtoUser;
import com.fantasticos.algorithmservice.dto.GrupoDTO;
import com.fantasticos.algorithmservice.model.Estudiante;
import com.fantasticos.algorithmservice.model.Grupo;
import com.fantasticos.algorithmservice.service.GrupoService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(GrupoController.class)
public class GrupoControllerTest {

	@Autowired
	private MockMvc mock;
	
	@MockBean
	private GrupoService service;
	
	@Test
	public void queSePuedaRegistrarUnGrupo() throws Exception {
		GrupoDTO grupo = modeloGrupo();
		
		when(service.cargarGrupo(grupo)).thenReturn(grupo());
		
		this.mock.perform(post("/algorithm/group/cargar")
				.content(asJsonString(grupo))
				.requestAttr("UserID", new DtoUser(grupo.getIdAdmin()))
				.contentType(MediaType.APPLICATION_JSON)
			    .accept(MediaType.APPLICATION_JSON))
				.andDo(print())
		 		.andExpect(status().isCreated())
		 		.andExpect(jsonPath("$.id", Matchers.notNullValue()));
	}
	
	@Test
	public void queSePuedaAgregarIntereses() throws Exception {
		GrupoDTO grupo = modeloGrupo();
		
		when(service.cargarInteres(grupo)).thenReturn(grupo());
		
		this.mock.perform(put("/algorithm/group/agregar-interes")
				.content(asJsonString(grupo))
				.requestAttr("UserID", new DtoUser(grupo.getIdAdmin()))
				.contentType(MediaType.APPLICATION_JSON)
			    .accept(MediaType.APPLICATION_JSON))
				.andDo(print())
		 		.andExpect(status().isOk())
		 		.andExpect(jsonPath("$.id", Matchers.notNullValue()));
	}
	
	@Test
	public void queSePuedaBorrarIntereses() throws Exception {
		GrupoDTO grupo = modeloGrupo();
		
		when(service.borrarInteres(grupo)).thenReturn(grupo());
		
		this.mock.perform(delete("/algorithm/group/borrar-interes")
				.content(asJsonString(grupo))
				.requestAttr("UserID", new DtoUser(grupo.getIdAdmin()))
				.contentType(MediaType.APPLICATION_JSON)
			    .accept(MediaType.APPLICATION_JSON))
				.andDo(print())
		 		.andExpect(status().isOk())
		 		.andExpect(jsonPath("$.id", Matchers.notNullValue()));
	}
	
	@Test
	public void queSePuedaAgregarMiembroAGrupo() throws Exception {
		GrupoDTO grupo = modeloGrupo();
		
		when(service.agregarMiembro(grupo)).thenReturn(grupo());
		
		this.mock.perform(put("/algorithm/group/agregar-miembro")
				.content(asJsonString(grupo))
				.requestAttr("UserID", new DtoUser(grupo.getIdAdmin()))
				.contentType(MediaType.APPLICATION_JSON)
			    .accept(MediaType.APPLICATION_JSON))
				.andDo(print())
		 		.andExpect(status().isOk())
		 		.andExpect(jsonPath("$.id", Matchers.notNullValue()));
	}
	
	@Test
	public void queSePuedaQuitarMiembro() throws Exception {
		GrupoDTO grupo = modeloGrupo();
		
		when(service.removerMiembro(grupo)).thenReturn(grupo());
		
		this.mock.perform(put("/algorithm/group/remover-miembro")
				.content(asJsonString(grupo))
				.requestAttr("UserID", new DtoUser(grupo.getIdAdmin()))
				.contentType(MediaType.APPLICATION_JSON)
			    .accept(MediaType.APPLICATION_JSON))
				.andDo(print())
		 		.andExpect(status().isOk())
		 		.andExpect(jsonPath("$.id", Matchers.notNullValue()));
	}
	
	@Test
	public void queSePuedaBorrarGrupo() throws Exception{
		GrupoDTO grupo = modeloGrupo();
		
		this.mock.perform(delete("/algorithm/group/borrar")
				.content(asJsonString(grupo))
				.requestAttr("UserID", new DtoUser(grupo.getIdAdmin()))
				.contentType(MediaType.APPLICATION_JSON)
			    .accept(MediaType.APPLICATION_JSON))
				.andDo(print())
		 		.andExpect(status().isOk());
		
		verify(service, times(1)).borrarGrupo(grupo);
	}
	

	private Grupo grupo() {
		Grupo g1 = new Grupo(200L);
		g1.setId(1L);
		g1.setAdministrador(administrador());
		g1.setMiembros(new ArrayList<Estudiante>(List.of(administrador())));
		return g1;
	}

	private Estudiante administrador() {
		return new Estudiante("aaa", 400l);
	}

	private GrupoDTO modeloGrupo() {
		GrupoDTO modelo = new GrupoDTO();
		
		modelo.setIdGrupo(200l);
		modelo.setIdAdmin("aaa");
		
		return modelo;
	}
	
	public static String asJsonString(final Object obj) {
	    try {
	        return new ObjectMapper().writeValueAsString(obj);
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}
}
