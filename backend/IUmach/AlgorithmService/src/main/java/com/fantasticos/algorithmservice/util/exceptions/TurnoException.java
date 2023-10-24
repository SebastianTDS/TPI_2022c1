package com.fantasticos.algorithmservice.util.exceptions;

public class TurnoException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public TurnoException() {
		super("No se indico el turno para cursar.");
	}
	
	public TurnoException(String message) {
		super(message);
	}

}
