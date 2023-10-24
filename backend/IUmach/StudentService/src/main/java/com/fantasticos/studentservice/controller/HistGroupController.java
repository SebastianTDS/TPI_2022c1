package com.fantasticos.studentservice.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fantasticos.studentservice.dto.DtoUser;
import com.fantasticos.studentservice.dto.HistDto;
import com.fantasticos.studentservice.service.HistService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/group")
@RequiredArgsConstructor
public class HistGroupController {
	private final HistService service;
    private final DtoUser dtoUser;
    
    @GetMapping(value = "/hist")
    public ResponseEntity<?> findByUser() {
        return new ResponseEntity<>(service.findByUser(dtoUser.getUserName()), HttpStatus.OK);
    }
    
    @PostMapping(value = "/hist/cargar")
    public ResponseEntity<?> findById(@RequestBody HistDto newHist) {
        return new ResponseEntity<>(service.createHistory(newHist), HttpStatus.OK);
    }
}
