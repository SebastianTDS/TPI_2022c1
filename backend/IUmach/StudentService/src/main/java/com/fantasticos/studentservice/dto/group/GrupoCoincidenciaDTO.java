package com.fantasticos.studentservice.dto.group;

import lombok.Data;

import java.util.List;

@Data
public class GrupoCoincidenciaDTO {

    private Long idGrupo;
    private List<String> tags;
    private Integer codigoMateria;
    private String nameMateria;
    private Double coincidencia;
}
