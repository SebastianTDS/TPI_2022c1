package com.example.filesservice.controller;

import com.example.filesservice.dto.FileGroupRequestDto;
import com.example.filesservice.dto.FileRequestDto;
import com.example.filesservice.dto.FileResponseDto;
import com.example.filesservice.dto.SizeDTO;
import com.example.filesservice.model.File;
import com.example.filesservice.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.nio.file.Files;
import java.util.List;

@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @GetMapping("/version")
    public String version() {
        return "FileService";
    }
    
    @GetMapping("/get-space/{id}")
    public ResponseEntity<?> repoSize(@PathVariable ("id")Long id) {
        return new ResponseEntity<>(new SizeDTO(fileService.checkSpace(id)), HttpStatus.OK);
    }


    @PostMapping("/group/upload")
    public ResponseEntity<File> uploadFile(@Valid @ModelAttribute FileGroupRequestDto fileGroupRequestDto, @RequestHeader("Authorization") String bearerToken) {
        return new ResponseEntity<>(fileService.uploadFile(fileGroupRequestDto, bearerToken,true), HttpStatus.CREATED);
    }

    @PostMapping("/group/upload/image")
    public ResponseEntity<File> uploadGroupImageProfile(@Valid @ModelAttribute FileGroupRequestDto fileGroupRequestDto, @RequestHeader("Authorization") String bearerToken) {
        return new ResponseEntity<>(fileService.uploadGroupImageProfile(fileGroupRequestDto, bearerToken,false), HttpStatus.CREATED);
    }

    @PostMapping(path = "/profile/upload")
    public ResponseEntity<File> uploadImageProfile(@Valid @ModelAttribute FileRequestDto fileRequestDto, @RequestHeader("Authorization") String bearerToken) {
        return new ResponseEntity<>(fileService.uploadImageProfile(fileRequestDto, bearerToken), HttpStatus.CREATED);
    }

    @GetMapping("/group/files/{idGroup}")
    public ResponseEntity<List<FileResponseDto>> getGroupFiles(@PathVariable ("idGroup")Long id, @RequestHeader("Authorization") String bearerToken){
        return new ResponseEntity<>(fileService.getGroupFiles(id,bearerToken),HttpStatus.OK);
    }

    @GetMapping("/profile/getImage/{idUser}")
    public ResponseEntity<FileResponseDto> getUrlImageProfile(@PathVariable ("idUser") String id,@RequestHeader("Authorization") String bearerToken){
        return new ResponseEntity<>(fileService.getUrlImageProfile(id,bearerToken),HttpStatus.OK);
    }
    @GetMapping("/group/profile/getImage/{idGroup}")
    public ResponseEntity<FileResponseDto> getGroupUrlImageProfile(@PathVariable ("idGroup") Long id,@RequestHeader("Authorization") String bearerToken){
        return new ResponseEntity<>(fileService.getGroupUrlImageProfile(id,bearerToken),HttpStatus.OK);
    }

    @DeleteMapping("/group/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGroupFile(@RequestBody FileRequestDto fileRequestDto,@RequestHeader("Authorization") String bearerToken){
        fileService.deleteGroupFile(fileRequestDto,bearerToken);
    }



}
