package com.fantasticos.studentservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fantasticos.studentservice.dto.DtoUser;
import com.fantasticos.studentservice.dto.KickDTO;
import com.fantasticos.studentservice.dto.ResponseDTO;
import com.fantasticos.studentservice.service.KickService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/group")
@RequiredArgsConstructor
public class KickController {

	private final KickService service;
    private final DtoUser dtoUser;
    
    @PostMapping("/kick/crear")
    public ResponseEntity<?> crearVotacion(@RequestHeader("Authorization") String bearerToken, @RequestBody KickDTO modelo){
    	ResponseDTO response = new ResponseDTO(service.nuevaVotacion(modelo, dtoUser.getUserName(), bearerToken));
    	return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @PutMapping("/kick/votar")
    public ResponseEntity<?> votar(@RequestHeader("Authorization") String bearerToken, @RequestBody KickDTO modelo){
    	ResponseDTO response = new ResponseDTO(service.votar(modelo, dtoUser.getUserName(), bearerToken));
    	return new ResponseEntity<>(response , HttpStatus.OK);
    }
    
    @DeleteMapping("/kick/rechazar")
    public ResponseEntity<?> rechazar(@RequestBody KickDTO modelo){
    	ResponseDTO response = new ResponseDTO(service.rechazar(modelo, dtoUser.getUserName()));
    	return new ResponseEntity<>(response , HttpStatus.OK);
    }
}
