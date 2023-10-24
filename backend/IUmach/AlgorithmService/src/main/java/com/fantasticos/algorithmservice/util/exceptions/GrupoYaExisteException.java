package com.fantasticos.algorithmservice.util.exceptions;

public class GrupoYaExisteException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public GrupoYaExisteException() {
		super("El grupo que intenta persistir ya existe");
	}
}
