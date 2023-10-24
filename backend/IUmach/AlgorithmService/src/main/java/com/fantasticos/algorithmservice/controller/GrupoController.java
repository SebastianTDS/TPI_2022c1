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
import com.fantasticos.algorithmservice.dto.GrupoDTO;
import com.fantasticos.algorithmservice.model.Grupo;
import com.fantasticos.algorithmservice.service.GrupoService;
import com.fantasticos.algorithmservice.util.exceptions.AuthFailedException;

@RestController
@RequestMapping("/algorithm/group")
public class GrupoController {

	@Autowired
	private GrupoService service;
	
	@PostMapping("/cargar")
	public ResponseEntity<?> cargarEstudiante(@RequestBody GrupoDTO nuevo, @RequestHeader("Authorization") String bearerToken) {
		Grupo cargado = service.cargarGrupo(nuevo.changeId(getMyId(bearerToken).getBody().getUserName()));
		
		if(cargado != null) return new ResponseEntity<>(cargado, HttpStatus.CREATED);
		
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@PutMapping("/agregar-interes")
	public ResponseEntity<?> agregarInteres(@RequestBody GrupoDTO objetivo, @RequestHeader("Authorization") String bearerToken) {
		Grupo cargado = service.cargarInteres(objetivo.changeId(getMyId(bearerToken).getBody().getUserName()));
		
		if(cargado != null) return new ResponseEntity<>(cargado, HttpStatus.OK);
		
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@PutMapping("/agregar-miembro")
	public ResponseEntity<?> agregarMiembro(@RequestBody GrupoDTO objetivo, @RequestHeader("Authorization") String bearerToken) {
		Grupo cargado = service.agregarMiembro(objetivo.changeId(getMyId(bearerToken).getBody().getUserName()));
		
		if(cargado != null) return new ResponseEntity<>(cargado, HttpStatus.OK);
		
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@PutMapping("/remover-miembro")
	public ResponseEntity<?> borrarMiembro(@RequestBody GrupoDTO objetivo, @RequestHeader("Authorization") String bearerToken) {
		Grupo cargado = service.removerMiembro(objetivo.changeId(getMyId(bearerToken).getBody().getUserName()));
		
		if(cargado != null) return new ResponseEntity<>(cargado, HttpStatus.OK);
		
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@DeleteMapping("/borrar-interes")
	public ResponseEntity<?> borrarInteres(@RequestBody GrupoDTO objetivo, @RequestHeader("Authorization") String bearerToken) {
		Grupo cargado = service.borrarInteres(objetivo.changeId(getMyId(bearerToken).getBody().getUserName()));
		
		if(cargado != null) return new ResponseEntity<>(cargado, HttpStatus.OK);
		
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@DeleteMapping("/borrar")
	public ResponseEntity<?> borrarGrupo(@RequestBody GrupoDTO objetivo, @RequestHeader("Authorization") String bearerToken) {
		service.borrarGrupo(objetivo.changeId(getMyId(bearerToken).getBody().getUserName()));
		
		return new ResponseEntity<>(HttpStatus.OK);
		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> verGrupo(@PathVariable("id") Long idGrupo) {
		Grupo cargado = service.buscarGrupo(idGrupo);
		
		if(cargado != null) return new ResponseEntity<>(cargado, HttpStatus.OK);
		
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	private static ResponseEntity<DtoUser> getMyId(String token){
        RestTemplate restTemplate = new RestTemplate();
        RequestEntity<Void> request = RequestEntity.get(URI.create("http://localhost:8761/student/my-id"))
                .header("Authorization", token)
                .build();
        try {        	
        	return restTemplate.exchange(request, DtoUser.class);
        }catch(Exception e) {
        	throw new AuthFailedException();
        }
    }
}
