package com.fantasticos.algorithmservice.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fantasticos.algorithmservice.dto.DtoUser;
import com.fantasticos.algorithmservice.dto.EstudianteDTO;
import com.fantasticos.algorithmservice.model.Estudiante;
import com.fantasticos.algorithmservice.service.UserService;
import com.fantasticos.algorithmservice.util.exceptions.AuthFailedException;

@RestController
@RequestMapping("/algorithm/user")
public class UserController {

	@Autowired
	private UserService service;
	
	@GetMapping("/{id}")
	public ResponseEntity<?> verEstudiante(@PathVariable("id") String idEstudiante) {
		Estudiante cargado = service.verEstudiante(idEstudiante);
		
		if(cargado != null) return new ResponseEntity<>(cargado, HttpStatus.OK);
		
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	
	@PostMapping("/cargar")
	public ResponseEntity<?> cargarEstudiante(@RequestBody EstudianteDTO nuevo, @RequestHeader("Authorization") String bearerToken) {
		System.out.println(bearerToken);
		Estudiante cargado = service.cargarEstudiante(nuevo.changeId(getMyId(bearerToken).getBody().getUserName()));

		if(cargado != null) return new ResponseEntity<>(cargado, HttpStatus.CREATED);
		
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@PutMapping("/anotar-carrera")
	public ResponseEntity<?> anotarEstudianteEnCarrera(@RequestBody EstudianteDTO objetivo, @RequestHeader("Authorization") String bearerToken){
		Estudiante actualizado = service.anotarCarrera(objetivo.changeId(getMyId(bearerToken).getBody().getUserName()));
		
		if(actualizado != null) return new ResponseEntity<>(actualizado, HttpStatus.OK);
		
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@PutMapping("/cambiar-facultad")
	public ResponseEntity<?> cambiarEstudianteDeFacultad(@RequestBody EstudianteDTO objetivo, @RequestHeader("Authorization") String bearerToken){
		Estudiante actualizado = service.cambiarFacultad(objetivo.changeId(getMyId(bearerToken).getBody().getUserName()));
		
		if(actualizado != null) return new ResponseEntity<>(actualizado, HttpStatus.OK);
		
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@PutMapping("/anotar-materia")
	public ResponseEntity<?> anotarEstudianteEnMateria(@RequestBody EstudianteDTO objetivo, @RequestHeader("Authorization") String bearerToken){
		Estudiante actualizado = service.anotarMateria(objetivo.changeId(getMyId(bearerToken).getBody().getUserName()));
		
		if(actualizado != null) return new ResponseEntity<>(actualizado, HttpStatus.OK);
		
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@PutMapping("/cargar-interes")
	public ResponseEntity<?> cargarInteres(@RequestBody EstudianteDTO objetivo, @RequestHeader("Authorization") String bearerToken){
		Estudiante actualizado = service.cargarInteres(objetivo.changeId(getMyId(bearerToken).getBody().getUserName()));
		
		if(actualizado != null) return new ResponseEntity<>(actualizado, HttpStatus.OK);
		
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@PutMapping("/sumar-puntos")
	public ResponseEntity<?> sumarPuntos(@RequestBody EstudianteDTO objetivo, @RequestHeader("Authorization") String bearerToken){
		Estudiante actualizado = service.sumarPuntos(objetivo);
		
		if(actualizado != null) return new ResponseEntity<>(actualizado, HttpStatus.OK);
		
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@DeleteMapping("/borrar-interes")
	public ResponseEntity<?> borrarInteres(@RequestBody EstudianteDTO objetivo, @RequestHeader("Authorization") String bearerToken){
		Estudiante actualizado = service.borrarInteres(objetivo.changeId(getMyId(bearerToken).getBody().getUserName()));
		
		if(actualizado != null) return new ResponseEntity<>(actualizado, HttpStatus.OK);
		
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@DeleteMapping("/abandonar-carrera")
	public ResponseEntity<?> abandonarCarrera(@RequestBody EstudianteDTO objetivo, @RequestHeader("Authorization") String bearerToken){
		Estudiante actualizado = service.abandonarCarrera(objetivo.changeId(getMyId(bearerToken).getBody().getUserName()));
		
		if(actualizado != null) return new ResponseEntity<>(actualizado, HttpStatus.OK);
		
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@DeleteMapping("/abandonar-materia")
	public ResponseEntity<?> abandonarMateria(@RequestBody EstudianteDTO objetivo, @RequestHeader("Authorization") String bearerToken){
		Estudiante actualizado = service.abandonarMateria(objetivo.changeId(getMyId(bearerToken).getBody().getUserName()));
		
		if(actualizado != null) return new ResponseEntity<>(actualizado, HttpStatus.OK);
		
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	private static ResponseEntity<DtoUser> getMyId(String token){
        RestTemplate restTemplate = new RestTemplate();
        RequestEntity<Void> request = RequestEntity.get(URI.create("http://localhost:8761/student/id"))
                .header("Authorization", token)
                .build();
        try {        	
        	return restTemplate.exchange(request, DtoUser.class);
        }catch(Exception e) {
        	throw new AuthFailedException();
        }
    }
}
