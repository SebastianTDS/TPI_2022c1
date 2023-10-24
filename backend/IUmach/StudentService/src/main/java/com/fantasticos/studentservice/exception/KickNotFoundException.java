package com.fantasticos.studentservice.exception;

public class KickNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public KickNotFoundException() {
		super("Votación inexistente");
	}

}
