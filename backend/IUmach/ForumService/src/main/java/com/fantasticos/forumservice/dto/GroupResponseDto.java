package com.fantasticos.forumservice.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class GroupResponseDto {

    private Long idGrupo;
    private String idAdmin;
    private String idMiembro;
    private Integer idMateria;
    private Long idInteres;

}
