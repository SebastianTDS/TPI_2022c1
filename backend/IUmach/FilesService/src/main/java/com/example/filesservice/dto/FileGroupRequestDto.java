package com.example.filesservice.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Data
public class FileGroupRequestDto {

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
    private Long idGroup;

    @NotNull
    private MultipartFile file;

}
