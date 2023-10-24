package com.fantasticos.algorithmservice.util.exceptions;

public class CarreraEnCursoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CarreraEnCursoException() {
		super("Ya se encuentra anotado en esa carrera.");
	}
}
