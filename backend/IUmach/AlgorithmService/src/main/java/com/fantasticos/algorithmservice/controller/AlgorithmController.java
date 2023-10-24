package com.fantasticos.algorithmservice.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fantasticos.algorithmservice.dto.EstudianteCoincidenciaDTO;
import com.fantasticos.algorithmservice.dto.FilterDTO;
import com.fantasticos.algorithmservice.dto.GrupoCoincidenciaDTO;
import com.fantasticos.algorithmservice.service.AlgorithmService;

@RestController
@RequestMapping("/algorithm")
public class AlgorithmController {
	
	@Autowired
	private AlgorithmService service;
	
	@GetMapping("/version")
	public String version() {
		return "AlgorithmService";
	}
	
	@GetMapping("/buscar-grupos")
	public ResponseEntity<?> buscarGruposFiltrados(@Valid FilterDTO busqueda){
		if(busqueda.getUser() == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		List<GrupoCoincidenciaDTO> grupos = service.buscarGrupos(busqueda);
		
		return ResponseEntity.ok(grupos);
	}
	
	@GetMapping("/buscar-estudiantes")
	public ResponseEntity<?> buscarEstudiantesFiltrados(@Valid FilterDTO busqueda){
		if(busqueda.getGroup() == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		List<EstudianteCoincidenciaDTO> grupos = service.buscarEstudiantes(busqueda);
		
		return ResponseEntity.ok(grupos);
	}
}
