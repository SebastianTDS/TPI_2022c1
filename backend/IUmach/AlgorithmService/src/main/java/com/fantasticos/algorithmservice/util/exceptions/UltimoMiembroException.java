package com.fantasticos.algorithmservice.util.exceptions;

public class UltimoMiembroException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UltimoMiembroException() {
		super("No puede salirse del grupo ya que no quedan miembros. Pruebe borrarlo.");
	}
}
