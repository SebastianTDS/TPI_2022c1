package com.fantasticos.algorithmservice.util.exceptions;

public class CarreraInexistenteException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CarreraInexistenteException() {
		super("La carrera solicitada no existe.");
	}
}
