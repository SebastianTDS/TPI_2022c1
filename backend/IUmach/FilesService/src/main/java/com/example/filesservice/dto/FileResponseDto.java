package com.example.filesservice.dto;

import lombok.*;

import java.util.Date;
@NoArgsConstructor
@Getter
@Setter
public class FileResponseDto {

    private String userName;

    private String name;

    private Date createAt;

    private String extension;

    private String url;

    private String originalName;



}
