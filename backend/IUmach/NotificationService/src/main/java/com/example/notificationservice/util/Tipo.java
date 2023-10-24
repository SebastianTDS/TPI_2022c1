package com.example.notificationservice.util;

public enum Tipo {
    UNION("Union"),
    ARCHIVO("Archivo"),
    MENSAJE("Mensaje"),
    SALIR("Salir"),
    SOLICITUD("Solicitud"),
    INVITACION("Invitacion"),
    CIERRE("Cierre"),
    RECORDATORIO("Recordatorio"),
    VOTACION("Votacion"),
    EXPULSION("Expulsion");

    private final String tipo;

    Tipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTipo() {
        return this.tipo;
    }
}
