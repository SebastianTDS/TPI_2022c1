package com.fantasticos.algorithmservice.util.exceptions;

public class AuthFailedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public AuthFailedException() {
		super("Fallo al autenticar la solicitud al servidor");
	}
}
