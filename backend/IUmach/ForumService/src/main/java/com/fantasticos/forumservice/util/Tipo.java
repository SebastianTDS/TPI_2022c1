package com.fantasticos.forumservice.util;

public enum Tipo {

    UNION("Union"),
    MENSAJE("Mensaje"),
    SALIR("Salir"),
    SOLICITUD("Solicitud"),
    INVITACION("Invitacion"),
    CIERRE("Cierre"),
    RECORDATORIO("Recordatorio"),
    ARCHIVO("Archivo");


    private final String tipo;

    Tipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTipo() {
        return this.tipo;
    }
}