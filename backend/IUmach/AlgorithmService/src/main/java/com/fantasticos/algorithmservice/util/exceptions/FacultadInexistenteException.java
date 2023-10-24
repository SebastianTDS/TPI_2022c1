package com.fantasticos.algorithmservice.util.exceptions;

public class FacultadInexistenteException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public FacultadInexistenteException() {
		super("La facultad del usuario no existe.");
	}
	
}
