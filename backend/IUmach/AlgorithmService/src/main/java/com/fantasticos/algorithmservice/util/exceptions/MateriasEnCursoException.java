package com.fantasticos.algorithmservice.util.exceptions;

public class MateriasEnCursoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public MateriasEnCursoException() {
		super("No se puede abandonar la carrera ya que se estan cursando materias de la misma.");
	}

	public MateriasEnCursoException(String message) {
		super(message);
	}
	
}
