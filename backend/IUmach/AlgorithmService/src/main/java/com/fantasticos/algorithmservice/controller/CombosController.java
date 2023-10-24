package com.fantasticos.algorithmservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fantasticos.algorithmservice.service.CombosService;

@RestController
@RequestMapping("/algorithm/graph")
public class CombosController {

	@Autowired
	private CombosService service;

	@GetMapping("/facultades")
	public ResponseEntity<?> verFacultades() {
		return new ResponseEntity<>(service.getFacultades(), HttpStatus.OK);
	}
	
	@GetMapping("/tags")
	public ResponseEntity<?> verTags() {
		return new ResponseEntity<>(service.getTags(), HttpStatus.OK);
	}

	@GetMapping("/{facultad}/departamentos")
	public ResponseEntity<?> verDepartamentos(@PathVariable("facultad") String facultad) {
		return new ResponseEntity<>(service.getDepartamentos(facultad), HttpStatus.OK);
	}

	@GetMapping("/{facultad}/{departamento}/carreras")
	public ResponseEntity<?> verCarreras(@PathVariable("facultad") String facultad,
			@PathVariable("departamento") Long departamento) {
		return new ResponseEntity<>(service.getCarreras(facultad, departamento), HttpStatus.OK);
	}
	
	@GetMapping("/{facultad}/{departamento}/{carrera}/materias")
	public ResponseEntity<?> verMaterias(@PathVariable("facultad") String facultad,
			@PathVariable("carrera") Long carrera){
		return new ResponseEntity<>(service.getMaterias(facultad, carrera), HttpStatus.OK);
	}
	
	@GetMapping("/{facultad}/{materia}/turnos")
	public ResponseEntity<?> verTurnos(@PathVariable("facultad") String facultad,
			@PathVariable("materia") Integer materia){
		return new ResponseEntity<>(service.getTurnosDisp(facultad, materia), HttpStatus.OK);
	}
}
