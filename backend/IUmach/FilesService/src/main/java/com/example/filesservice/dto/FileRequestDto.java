package com.example.filesservice.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class FileRequestDto {

    @NotNull
    @NotBlank
    @NotEmpty
    private String idUser;

    @NotNull
    @NotBlank
    @NotEmpty
    private String userName;

    @NotNull
    @NotBlank
    @NotEmpty
    private String name;

    @NotNull
    private MultipartFile file;

    private Long idGroup;
}
