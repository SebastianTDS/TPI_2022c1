package com.fantasticos.algorithmservice.util.exceptions;

public class MateriaInexistenteException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public MateriaInexistenteException() {
		super("La materia solicitada no existe.");
	}
	
	public MateriaInexistenteException(String message) {
		super(message);
	}
}
