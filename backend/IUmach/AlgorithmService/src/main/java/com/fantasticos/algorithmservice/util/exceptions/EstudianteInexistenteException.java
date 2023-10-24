package com.fantasticos.algorithmservice.util.exceptions;

public class EstudianteInexistenteException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EstudianteInexistenteException() {
		super("El usuario no existe.");
	}
	
	public EstudianteInexistenteException(String message) {
		super(message);
	}
}
