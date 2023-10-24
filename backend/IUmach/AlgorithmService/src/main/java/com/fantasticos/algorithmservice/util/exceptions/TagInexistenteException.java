package com.fantasticos.algorithmservice.util.exceptions;

public class TagInexistenteException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public TagInexistenteException() {
		super("El tag solicitado no existe");
	}
	
	public TagInexistenteException(String message) {
		super(message);
	}
	
}
