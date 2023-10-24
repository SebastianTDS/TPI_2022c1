package com.fantasticos.algorithmservice.util.exceptions;

public class GrupoInexistenteException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public GrupoInexistenteException() {
		super("El grupo solicitado no existe!");
	}
	
	public GrupoInexistenteException(String message) {
		super(message);
	}
	
}
