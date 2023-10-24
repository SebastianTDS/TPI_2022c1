package com.fantasticos.algorithmservice.util.exceptions;

public class EstudiantePersistidoException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public EstudiantePersistidoException() {
		super("El estudiante ya esta registrado en el sistema.");
	}

	public EstudiantePersistidoException(String message) {
		super(message);
	}

}
