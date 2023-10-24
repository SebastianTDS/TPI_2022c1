package com.fantasticos.studentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DtoValorationRequest {
    private Long idValorationNotification;
    private String valuationIn;
    private boolean responsable;
    private boolean colaborador;
    private boolean proActivo;
    private boolean organizado;
    private boolean conocedor;
    private boolean detallista;
}
