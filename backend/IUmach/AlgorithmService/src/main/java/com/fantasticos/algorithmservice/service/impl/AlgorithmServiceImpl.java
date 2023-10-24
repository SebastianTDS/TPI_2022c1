package com.fantasticos.algorithmservice.service.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.neo4j.driver.internal.InternalNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.fantasticos.algorithmservice.dto.EstudianteCoincidenciaDTO;
import com.fantasticos.algorithmservice.dto.FilterDTO;
import com.fantasticos.algorithmservice.dto.GrupoCoincidenciaDTO;
import com.fantasticos.algorithmservice.model.Grupo;
import com.fantasticos.algorithmservice.model.Materia;
import com.fantasticos.algorithmservice.model.Tag;
import com.fantasticos.algorithmservice.repository.AlgorithmRepository;
import com.fantasticos.algorithmservice.service.AlgorithmService;

@Service
public class AlgorithmServiceImpl implements AlgorithmService {

	@Autowired
	private AlgorithmRepository repoAlg;
	
	@Override
	public List<GrupoCoincidenciaDTO> buscarGrupos(FilterDTO busqueda) {
		return buildResponse(repoAlg.getGruposByCoincidence(busqueda, PageRequest.of(busqueda.getPagina(), 9)));
	}
	
	@Override
	public List<EstudianteCoincidenciaDTO> buscarEstudiantes(FilterDTO busqueda) {
		return buildResponseEst(repoAlg.getEstudiantesByCoincidence(busqueda, PageRequest.of(busqueda.getPagina(), 9)));
	}
	
	@SuppressWarnings("unchecked")
	private List<EstudianteCoincidenciaDTO> buildResponseEst(Page<Map<String, Object>> map) {
		return map.getContent()
				.stream()
				.map( m -> {
					EstudianteCoincidenciaDTO dto = buildEstudiante(
							(InternalNode) m.get("estudiante"),
							(List<InternalNode>) m.get("tags"),
							(List<InternalNode>) m.get("materias"),
							(List<InternalNode>) m.get("carreras"),
							(InternalNode) m.get("facultad"));
					
					dto.setCoincidencia((Double) m.get("compatibilidad"));

					return dto;
				}).collect(Collectors.toList());
	}
	
	@SuppressWarnings("unchecked")
	private List<GrupoCoincidenciaDTO> buildResponse(Page<Map<String, Object>> map) {
		return map.getContent()
				.stream()
				.map( m -> {
					GrupoCoincidenciaDTO dto = new GrupoCoincidenciaDTO();
					Grupo g = buildGrupo((InternalNode) m.get("grupo"), (List<InternalNode>) m.get("tags"), (InternalNode) m.get("materia"));
					
					dto.setIdGrupo(g.getParKey());
					dto.setCoincidencia((Double) m.get("compatibilidad"));
					if(g.getMateria() != null) {
						dto.setCodigoMateria(g.getMateria().getCodigo());
						dto.setNameMateria(g.getMateria().getTitle());
					}
					dto.setTags(g.getIntereses().stream().map(t -> {return t.getName();}).collect(Collectors.toList()));
					
					return dto;
				}).collect(Collectors.toList());
	}
	
	private EstudianteCoincidenciaDTO buildEstudiante(InternalNode estudiante, List<InternalNode> tags, List<InternalNode> materias,
			List<InternalNode> carreras, InternalNode facultad) 
	{
		EstudianteCoincidenciaDTO objetivo = new EstudianteCoincidenciaDTO();
		
		objetivo.setIdEstudiante(estudiante.get("parKey").asString());
		objetivo.setTags(buildTags(tags).stream().map(t -> {return t.getName();} ).collect(Collectors.toList()));
		objetivo.setMaterias(buildMaterias(materias));
		objetivo.setCarreras(buildCarreras(carreras));
		objetivo.setFacultad(buildFacultad(facultad));
		
		return objetivo;
	}

	private String buildFacultad(InternalNode facultad) {
		if(facultad == null) return null;
		
		return facultad.get("abr").asString() + " - " + facultad.get("name").asString();
	}

	private List<String> buildCarreras(List<InternalNode> carreras) {
		if(carreras.isEmpty()) return List.of();
		return carreras.stream().map(t ->{
			return t.get("name").asString();
		}).collect(Collectors.toList());
	}

	private List<String> buildMaterias(List<InternalNode> materias) {
		if(materias.isEmpty()) return List.of();
		return materias.stream().map(t ->{
			return t.get("codigo").asInt() + " - " +  t.get("title").asString();
		}).collect(Collectors.toList());
	}
	
	private List<Tag> buildTags(List<InternalNode> nodes) {
		if(nodes.isEmpty()) return List.of();
		return nodes.stream().map(t ->{
			Tag tag = new Tag();
			
			tag.setId(t.id());
			tag.setName(t.get("name").asString());
			
			return tag;
		}).collect(Collectors.toList());
	}

	private Materia buildMateria(InternalNode node) {
		if(node == null) return null;
		
		Materia objetivo = new Materia();
		
		objetivo.setId(node.id());
		objetivo.setCodigo(node.get("codigo").asInt());
		objetivo.setTitle(node.get("title").asString());
		
		return objetivo;
	}

	private Grupo buildGrupo(InternalNode grupo, List<InternalNode> tags, InternalNode materia) {
		Grupo objetivo = new Grupo();
		
		objetivo.setParKey(grupo.get("parKey").asLong());
		objetivo.setId(grupo.id());
		objetivo.setIntereses(buildTags(tags));
		objetivo.setMateria(buildMateria(materia));
		
		return objetivo;
	}


}
