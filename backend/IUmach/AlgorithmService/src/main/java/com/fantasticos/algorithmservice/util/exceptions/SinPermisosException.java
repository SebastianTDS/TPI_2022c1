package com.fantasticos.algorithmservice.util.exceptions;

public class SinPermisosException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public SinPermisosException() {
		super("No tiene permiso para realizar la operación.");
	}
	
	public SinPermisosException(String message) {
		super(message);
	}
}
