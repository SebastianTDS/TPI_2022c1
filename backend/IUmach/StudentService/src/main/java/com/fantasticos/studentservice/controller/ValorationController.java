package com.fantasticos.studentservice.controller;

import com.fantasticos.studentservice.dto.*;
import com.fantasticos.studentservice.service.ValorationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/valoration")
@RequiredArgsConstructor
@Slf4j
public class ValorationController {
	private final ValorationService valorationService;
	@Autowired
	private DtoUser user;

	@GetMapping("/version")
	public ResponseEntity<String> version() {
		return ResponseEntity.ok("Hola ValorationController Version");
	}


	@GetMapping("/get-valoration")
	public ResponseEntity<List<DtoValorationNotification>> getValoration() {
		return new ResponseEntity<>(valorationService.getValorationNotification(user.getUserName()),HttpStatus.OK);
	}

	@PutMapping("/valoration")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public void valoration(@RequestBody DtoValorationRequest valorationRequest) {
		valorationService.valoration(valorationRequest);
	}

	@PostMapping("/create-valoration")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public void createValoration(@RequestBody DtoValorationCreatedRequest dtoValorationCreatedRequest) {
		valorationService.create(dtoValorationCreatedRequest);
	}

	@PutMapping("/valoration-like/{id}")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public void valorationLike(@PathVariable("id") String userId) {
		valorationService.valorationLike(userId);
	}


}
