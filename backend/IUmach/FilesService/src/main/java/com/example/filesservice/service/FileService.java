package com.example.filesservice.service;

import com.example.filesservice.dto.FileGroupRequestDto;
import com.example.filesservice.dto.FileRequestDto;
import com.example.filesservice.dto.FileResponseDto;
import com.example.filesservice.model.File;

import java.util.List;

public interface FileService {
    File uploadFile(FileGroupRequestDto fileGroupRequestDto, String bearerToken,Boolean isFile);

    File uploadGroupImageProfile(FileGroupRequestDto fileGroupRequestDto, String bearerToken,Boolean isFile);

    File uploadImageProfile(FileRequestDto fileRequestDto, String bearerToken);

    List<FileResponseDto> getGroupFiles(Long id, String bearerToken);

    FileResponseDto getUrlImageProfile(String id, String bearerToken);

    FileResponseDto getGroupUrlImageProfile(Long id, String bearerToken);

    void deleteGroupFile(FileRequestDto fileRequestDto, String bearerToken);
    
    Long checkSpace(Long idGroup);
}
