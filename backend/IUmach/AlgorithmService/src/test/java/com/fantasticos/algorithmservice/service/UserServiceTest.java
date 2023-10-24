package com.fantasticos.algorithmservice.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
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

import com.fantasticos.algorithmservice.model.Facultad;
import com.fantasticos.algorithmservice.model.Materia;
import com.fantasticos.algorithmservice.model.Tag;
import com.fantasticos.algorithmservice.dto.EstudianteDTO;
import com.fantasticos.algorithmservice.model.Carrera;
import com.fantasticos.algorithmservice.model.Estudiante;
import com.fantasticos.algorithmservice.repository.CarreraRepository;
import com.fantasticos.algorithmservice.repository.EstudianteRepository;
import com.fantasticos.algorithmservice.repository.FacultadRepository;
import com.fantasticos.algorithmservice.repository.MateriaRepository;
import com.fantasticos.algorithmservice.repository.TagRepository;
import com.fantasticos.algorithmservice.util.enums.Turno;

@SpringBootTest
public class UserServiceTest {

	@Autowired
	private UserService service;
	
	@MockBean private EstudianteRepository repoEst;
	@MockBean private FacultadRepository repoFacu;
	@MockBean private CarreraRepository repoCarrera;
	@MockBean private MateriaRepository repoMateria;
	@MockBean private TagRepository repoTags;
	
	@Test
	public void queSePuedaCargarNuevoEstudiante() {
		when(repoFacu.findById(anyLong())).thenReturn(facultad());
		when(repoEst.save(estudiante().get())).thenReturn(estudiante().get());
		
		Estudiante cargado = service.cargarEstudiante(modeloEstudiante());
		
		verify(repoFacu, times(1)).findById(anyLong());
		assertThat(cargado).isNotNull();
	}
	
	@Test
	public void queSePuedaAnotarEstudianteEnCarrera() {
		EstudianteDTO dto = modeloEstudiante();
		
		when(repoFacu.findById(dto.getIdFacultad())).thenReturn(facultad());
		when(repoCarrera.findByFacultad(dto.getIdCarrera(), dto.getIdFacultad())).thenReturn(carrera());
		when(repoEst.findByParKey(dto.getId())).thenReturn(estudiante());
		when(repoEst.save(carreraCargada())).thenReturn(carreraCargada());
		
		Estudiante actualizado = service.anotarCarrera(dto);
		
		assertThat(actualizado).isNotNull();
		assertThat(actualizado.getCarreras()).hasSize(1);
	}
	
	@Test
	public void queSePuedaAbandonarCarrera() {
		EstudianteDTO dto = modeloEstudiante();
		
		when(repoCarrera.findByAlumno(dto.getIdCarrera(), dto.getId())).thenReturn(carrera());
		when(repoEst.findByParKey(dto.getId())).thenReturn(Optional.of(materiaCargada()));
		when(repoMateria.materiasPend(dto.getIdCarrera(), dto.getId())).thenReturn(List.of(materia().get()));
		when(repoEst.save(estudiante().get())).thenReturn(estudiante().get());
		
		Estudiante actualizado = service.abandonarCarrera(dto);
		
		assertThat(actualizado).isNotNull();
		assertThat(actualizado.getCarreras()).isEmpty();
	}
	
	@Test
	public void queSePuedaAnotarEstudianteEnMateria() {
		EstudianteDTO dto = modeloEstudiante();
		
		when(repoMateria.findByCarrera(dto.getIdMateria(), dto.getIdCarrera())).thenReturn(materia());
		when(repoMateria.isTurnoDisponible(dto.getIdMateria(), dto.getTurno().getNodo())).thenReturn(true);
		when(repoEst.findByParKey(dto.getId())).thenReturn(Optional.of(carreraCargada()));
		when(repoEst.save(materiaCargada())).thenReturn(materiaCargada());
		
		Estudiante actualizado = service.anotarMateria(dto);
		
		assertThat(actualizado).isNotNull();
		assertThat(actualizado.getMateriasDia()).isNotEmpty();
		assertThat(actualizado.getMateriasTarde()).isEmpty();
		assertThat(actualizado.getMateriasNoche()).isEmpty();
	}
	
	@Test
	public void queSePuedaAbandonarMateria() {
		EstudianteDTO dto = modeloEstudiante();
		
		when(repoEst.findByParKey(dto.getId())).thenReturn(Optional.of(materiaCargada()));
		when(repoEst.isCursandoMateria(dto.getId(), dto.getIdMateria())).thenReturn(true);
		when(repoMateria.findByCodigo(dto.getIdMateria())).thenReturn(materia());
		when(repoEst.save(carreraCargada())).thenReturn(carreraCargada());
		
		Estudiante actualizado = service.abandonarMateria(dto);
		
		assertThat(actualizado).isNotNull();
		assertThat(actualizado.getMateriasDia()).isEmpty();
		assertThat(actualizado.getMateriasTarde()).isEmpty();
		assertThat(actualizado.getMateriasNoche()).isEmpty();
	}
	
	@Test
	public void queSePuedaCargarInteres() {
		EstudianteDTO dto = modeloEstudiante();
		
		when(repoTags.findById(dto.getIdInteres())).thenReturn(tag());
		when(repoEst.findByParKey(dto.getId())).thenReturn(estudiante());
		when(repoEst.save(interesCargado())).thenReturn(interesCargado());
		
		Estudiante actualizado = service.cargarInteres(dto);
		
		assertThat(actualizado).isNotNull();
		assertThat(actualizado.getIntereses()).isNotEmpty();
	}
	
	@Test
	public void queSePuedaBorrarInteres() {
		EstudianteDTO dto = modeloEstudiante();
		
		when(repoTags.findById(dto.getIdInteres())).thenReturn(tag());
		when(repoEst.findByParKey(dto.getId())).thenReturn(Optional.of(interesCargado()));
		when(repoEst.save(estudiante().get())).thenReturn(estudiante().get());
		
		Estudiante actualizado = service.borrarInteres(dto);
		
		assertThat(actualizado).isNotNull();
		assertThat(actualizado.getIntereses()).isEmpty();
	}
	
	@Test
	public void queSePuedaPuntuarEstudiante() {
		EstudianteDTO dto = modeloPuntos();
		
		when(repoEst.findByParKey(dto.getId())).thenReturn(estudiante());
		when(repoEst.save(estudiantePuntuado())).thenReturn(estudiantePuntuado());
		
		Estudiante actualizado = service.sumarPuntos(dto);
		
		assertThat(actualizado).isNotNull();
		assertThat(actualizado.getPuntos()).isGreaterThan(dto.getPuntos());
	}
	
	private Estudiante estudiantePuntuado() {
		Estudiante e1 = new Estudiante("aaa", 1000L);
		e1.setFacultad(facultad().get());
		return e1;
	}

	private EstudianteDTO modeloPuntos() {
		EstudianteDTO dto = new EstudianteDTO();
		
		dto.setId("aaa");
		dto.setPuntos(500L);
		
		return dto;
	}

	private Estudiante interesCargado() {
		Estudiante e1 = new Estudiante("aaa", 500L);
		e1.setFacultad(facultad().get());
		e1.setIntereses(new ArrayList<Tag>(List.of(tag().get())) );
		return e1;
	}

	private Optional<Tag> tag() {
		return Optional.of(new Tag(44L, "WebDev"));
	}

	private Estudiante materiaCargada() {
		Estudiante e1 = new Estudiante("aaa", 500L);
		e1.setFacultad(facultad().get());
		e1.setCarreras(new ArrayList<Carrera>(List.of(carrera().get())) );
		e1.setMateriasDia(new ArrayList<Materia>(List.of(materia().get())) );
		return e1;
	}

	private Optional<Materia> materia() {
		return Optional.of(new Materia(1L, 3001, "Matemática General I"));
	}

	private Optional<Carrera> carrera() {
		return Optional.of(new Carrera(1L, "Tecnicatura en desarrollo Móvil"));
	}

	private EstudianteDTO modeloEstudiante() {
		EstudianteDTO dto = new EstudianteDTO();
		
		dto.setPuntos(500L);
		dto.setId("aaa");
		dto.setIdFacultad(0L);
		dto.setIdCarrera(1L);
		dto.setIdMateria(3001);
		dto.setIdInteres(44L);
		dto.setTurno(Turno.DIA);
		
		return dto;
	}

	private Optional<Estudiante> estudiante() {
		Estudiante e1 = new Estudiante("aaa", 500L);
		e1.setFacultad(facultad().get());
		return Optional.of(e1);
	}
	
	private Estudiante carreraCargada() {
		Estudiante e1 = new Estudiante("aaa", 500L);
		e1.setFacultad(facultad().get());
		e1.setCarreras(new ArrayList<Carrera>(List.of(carrera().get())) );
		return e1;
	}
	
	
	private Optional<Facultad> facultad() {
		return Optional.of(new Facultad(0L, "UNLAM", "Universidad Nacional de la Matanza"));
	}
}
