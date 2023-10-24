package com.fantasticos.studentservice.controller;

import com.fantasticos.studentservice.dto.*;
import com.fantasticos.studentservice.dto.group.GroupCreateDto;

import com.fantasticos.studentservice.dto.group.GroupDeleteDto;
import com.fantasticos.studentservice.dto.group.GroupDto;
import com.fantasticos.studentservice.dto.group.GroupUpdateDto;
import com.fantasticos.studentservice.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/group")
@RequiredArgsConstructor
public class GroupController {

    private final GroupService service;
    private final DtoUser dtoUser;

    @GetMapping(value = "/{id}")
    public ResponseEntity<GroupDto> findById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<GroupDto>> findAll(){
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@Valid @RequestBody GroupCreateDto group, @RequestHeader("Authorization") String bearerToken){
        service.save(group, dtoUser.getUserName(),bearerToken);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody GroupUpdateDto group, @PathVariable Long id){
        service.update(id,group);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestHeader("Authorization") String bearerToken,@RequestBody GroupDeleteDto groupDeleteDto){
        service.delete(groupDeleteDto.getId(),bearerToken, dtoUser.getUserName());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/join/{idGroup}")
    public ResponseEntity<?> joinOpenGroup(@RequestHeader("Authorization") String bearerToken,@PathVariable Long idGroup){
        service.joinGroup(dtoUser.getUserName(), idGroup, bearerToken);
        return  new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/leave/{idGroup}")
    public ResponseEntity<?> leaveGroup(@RequestHeader("Authorization") String bearerToken, @PathVariable Long idGroup){
        service.leaveGroup(dtoUser.getUserName(), idGroup, bearerToken);
        return  new ResponseEntity<>(HttpStatus.OK);
    }
    
    @GetMapping("/my-groups")
    public ResponseEntity<List<GroupDto>> myGroups(){
        return new ResponseEntity<>(service.findMyGroups(dtoUser.getUserName()),HttpStatus.OK);
    }

    @GetMapping("/recommended-groups")
    public ResponseEntity<Object> recommendedGroups(FilterDto search){
        return new ResponseEntity<>(service.findRecommendedGroups(search, dtoUser.getUserName()),HttpStatus.OK);
    }
    
    @GetMapping("/recommended-students")
    public ResponseEntity<Object> recommendedStudents(FilterDto search){
        return new ResponseEntity<>(service.findRecommendedStudents(search, dtoUser.getUserName()),HttpStatus.OK);
    }
}
