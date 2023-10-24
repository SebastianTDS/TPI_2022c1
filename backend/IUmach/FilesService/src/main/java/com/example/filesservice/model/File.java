package com.example.filesservice.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "files")
public class File {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private Date createAt = new Date();

    private String idUser;

    private String userName;

    private String name;

    private Long idGroup;

    private Boolean isFile;

    private String originalName;

    private String extension;
    
    private Long size;

}
