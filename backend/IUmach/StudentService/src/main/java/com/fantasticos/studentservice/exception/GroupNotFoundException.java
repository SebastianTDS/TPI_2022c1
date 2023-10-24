package com.fantasticos.studentservice.exception;

public class GroupNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	public GroupNotFoundException() {super("Grupo no encontrado");}
    public GroupNotFoundException(String str){ super(str);}
}
