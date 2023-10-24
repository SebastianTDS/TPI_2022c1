package com.fantasticos.algorithmservice.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.fantasticos.algorithmservice.dto.GrupoDTO;
import com.fantasticos.algorithmservice.model.Estudiante;
import com.fantasticos.algorithmservice.model.Grupo;
import com.fantasticos.algorithmservice.model.Tag;
import com.fantasticos.algorithmservice.repository.EstudianteRepository;
import com.fantasticos.algorithmservice.repository.FacultadRepository;
import com.fantasticos.algorithmservice.repository.GrupoRepository;
import com.fantasticos.algorithmservice.repository.MateriaRepository;
import com.fantasticos.algorithmservice.repository.TagRepository;

@SpringBootTest
public class GrupoServiceTest {
	
	@Autowired
	private GrupoService service;
	
	@MockBean private EstudianteRepository repoEst;
	@MockBean private FacultadRepository repoFacu;
	@MockBean private GrupoRepository repoGrupo;
	@MockBean private MateriaRepository repoMateria;
	@MockBean private TagRepository repoTags;
	
	@Test
	public void queSePuedaCargarGrupo() {
		GrupoDTO modelo = modeloGrupo();
		
		when(repoEst.findByParKey(modelo.getIdAdmin())).thenReturn(administrador());
		when(repoGrupo.save(grupo())).thenReturn(grupo());
		
		Grupo cargado = service.cargarGrupo(modeloGrupo());
		
		assertThat(cargado).isNotNull();
		assertThat(cargado.getMiembros()).contains(administrador().get());
	}
	
	@Test
	public void queSePuedaCargarUnInteresDeGrupo() {
		GrupoDTO modelo = modeloGrupo();
		
		when(repoGrupo.findByParKey(modelo.getIdGrupo())).thenReturn(Optional.of(grupo()));
		when(repoTags.findById(modelo.getIdInteres())).thenReturn(tag());
		when(repoGrupo.save(grupoConInteres())).thenReturn(grupoConInteres());
		
		Grupo actualizado = service.cargarInteres(modelo);
		
		assertThat(actualizado).isNotNull();
		assertThat(actualizado.getIntereses()).isNotEmpty();
	}
	
	@Test
	public void queSePuedaBorrarInteresDeGrupo() {
		GrupoDTO modelo = modeloGrupo();
		
		when(repoGrupo.findByParKey(modelo.getIdGrupo())).thenReturn(Optional.of(grupoConInteres()));
		when(repoTags.findById(modelo.getIdInteres())).thenReturn(tag());
		when(repoGrupo.save(grupo())).thenReturn(grupo());
		
		Grupo actualizado = service.borrarInteres(modelo);
		
		assertThat(actualizado).isNotNull();
		assertThat(actualizado.getIntereses()).isEmpty();
	}
	
	@Test
	public void queSePuedaBorrarGrupo() {
		GrupoDTO modelo = modeloGrupo();
		
		when(repoGrupo.findByParKey(modelo.getIdGrupo())).thenReturn(Optional.of(grupo()));
		
		service.borrarGrupo(modelo);
		
		verify(repoGrupo, times(1)).delete(grupo());
	}
	
	@Test
	public void queSePuedaAgregarMiembroAGrupo() {
		GrupoDTO modelo = modeloGrupo();
		
		when(repoGrupo.findByParKey(modelo.getIdGrupo())).thenReturn(Optional.of(grupo()));
		when(repoEst.findByParKey(modelo.getIdMiembro())).thenReturn(estudiante());
		when(repoGrupo.save(grupoConIntegrante())).thenReturn(grupoConIntegrante());
		
		Grupo actualizado = service.agregarMiembro(modelo);
		
		assertThat(actualizado).isNotNull();
		assertThat(actualizado.getMiembros()).hasSize(2);
	}
	
	@Test
	public void queSePuedaBorrarMiembroAGrupo() {
		GrupoDTO modelo = modeloGrupo();
		
		when(repoGrupo.findByParKey(modelo.getIdGrupo())).thenReturn(Optional.of(grupoConIntegrante()));
		when(repoGrupo.save(grupoNuevoAdmin())).thenReturn(grupoNuevoAdmin());
		
		Grupo actualizado = service.removerMiembro(modelo);
		
		assertThat(actualizado).isNotNull();
		assertThat(actualizado.getMiembros()).hasSize(1);
	}

	private Grupo grupoNuevoAdmin() {
		Grupo g1 = new Grupo(200L);
		g1.setParKey(200L);
		g1.setAdministrador(estudiante().get());
		g1.setMiembros(new ArrayList<Estudiante>(List.of(estudiante().get())));
		return g1;
	}

	private Grupo grupoConIntegrante() {
		Grupo g1 = new Grupo(200L);
		g1.setParKey(200L);
		g1.setAdministrador(administrador().get());
		g1.setMiembros(new ArrayList<Estudiante>(List.of(administrador().get())));
		g1.getMiembros().add(estudiante().get());
		return g1;
	}

	private Optional<Estudiante> estudiante() {
		Estudiante e1 = new Estudiante("bbb", 500L);
		return Optional.of(e1);
	}

	private Grupo grupoConInteres() {
		Grupo g1 = new Grupo(200L);
		g1.setParKey(200L);
		g1.setAdministrador(administrador().get());
		g1.setMiembros(new ArrayList<Estudiante>(List.of(administrador().get())));
		g1.getIntereses().add(tag().get());
		return g1;
	}

	private Optional<Tag> tag() {
		return Optional.of(new Tag(44L, "WebDev"));
	}

	private Grupo grupo() {
		Grupo g1 = new Grupo(200L);
		g1.setParKey(200L);
		g1.setAdministrador(administrador().get());
		g1.setMiembros(new ArrayList<Estudiante>(List.of(administrador().get())));
		return g1;
	}

	private Optional<Estudiante> administrador() {
		return Optional.of(new Estudiante("aaa", 400l));
	}

	private GrupoDTO modeloGrupo() {
		GrupoDTO modelo = new GrupoDTO();
		
		modelo.setIdGrupo(200l);
		modelo.setIdAdmin("aaa");
		modelo.setIdInteres(44L);
		
		return modelo;
	}
}
