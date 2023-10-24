package com.fantasticos.algorithmservice.util.enums;

public enum Turno {
	DIA("Dia"),
	TARDE("Tarde"),
	NOCHE("Noche");
	
	private final String nodo;
	
    Turno(String nodo) {
		this.nodo = nodo;
	}
    
    public String getNodo() {
    	return this.nodo;
    }
}
