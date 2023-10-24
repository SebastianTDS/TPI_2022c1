package com.fantasticos.algorithmservice.util.exceptions;

public class InteresYaCargadoException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public InteresYaCargadoException() {
		super("El estudiante ya tiene cargado ese interes");
	}

	public InteresYaCargadoException(String message) {
		super(message);
	}

}
