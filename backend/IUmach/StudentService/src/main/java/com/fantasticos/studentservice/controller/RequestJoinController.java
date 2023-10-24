package com.fantasticos.studentservice.controller;

import com.fantasticos.studentservice.dto.DtoUser;
import com.fantasticos.studentservice.dto.request.RequestJoinDataDto;
import com.fantasticos.studentservice.dto.request.RequestJoinDto;
import com.fantasticos.studentservice.service.RequestJoinService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/request-join")
@Slf4j
@RequiredArgsConstructor
public class RequestJoinController {

    private final RequestJoinService service;
    private final DtoUser dtoUser;

    @GetMapping(value = "/{id}")
    public ResponseEntity<RequestJoinDto> findById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<RequestJoinDto>> findAll(){
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestHeader("Authorization") String bearerToken,@Valid @RequestBody RequestJoinDataDto request){
        service.save(request, dtoUser.getUserName(), bearerToken);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    
    @PostMapping("/invite")
    public ResponseEntity<?> invite(@RequestHeader("Authorization") String bearerToken,@Valid @RequestBody RequestJoinDataDto request){
        service.invite(request, dtoUser.getUserName(), bearerToken);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    
    @PostMapping("/invite-email")
    public ResponseEntity<?> inviteEmail(@RequestHeader("Authorization") String bearerToken,@Valid @RequestBody RequestJoinDataDto request){
        service.inviteEmail(request, dtoUser.getUserName(), bearerToken);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/accept/{idGroup}/{idStudent}")
    public ResponseEntity<?> accept(@RequestHeader("Authorization") String bearerToken,
                                    @PathVariable Long idGroup,
                                    @PathVariable String idStudent){
        service.acceptRequestJoin(idGroup, idStudent, bearerToken);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/reject/{idGroup}/{idStudent}")
    public ResponseEntity<?> reject(@PathVariable Long idGroup,
                                    @PathVariable String idStudent){
        service.rejectRequestJoin(idGroup, idStudent);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
