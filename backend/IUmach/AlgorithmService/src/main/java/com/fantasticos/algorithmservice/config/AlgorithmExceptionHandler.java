package com.fantasticos.algorithmservice.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fantasticos.algorithmservice.util.ApiError;
import com.fantasticos.algorithmservice.util.exceptions.AuthFailedException;
import com.fantasticos.algorithmservice.util.exceptions.CarreraEnCursoException;
import com.fantasticos.algorithmservice.util.exceptions.CarreraInexistenteException;
import com.fantasticos.algorithmservice.util.exceptions.EstudianteInexistenteException;
import com.fantasticos.algorithmservice.util.exceptions.EstudiantePersistidoException;
import com.fantasticos.algorithmservice.util.exceptions.FacultadInexistenteException;
import com.fantasticos.algorithmservice.util.exceptions.GrupoInexistenteException;
import com.fantasticos.algorithmservice.util.exceptions.GrupoYaExisteException;
import com.fantasticos.algorithmservice.util.exceptions.InteresYaCargadoException;
import com.fantasticos.algorithmservice.util.exceptions.MateriaInexistenteException;
import com.fantasticos.algorithmservice.util.exceptions.MateriasEnCursoException;
import com.fantasticos.algorithmservice.util.exceptions.SinPermisosException;
import com.fantasticos.algorithmservice.util.exceptions.TagInexistenteException;
import com.fantasticos.algorithmservice.util.exceptions.TurnoException;

@ControllerAdvice
public class AlgorithmExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(EstudiantePersistidoException.class)
	protected ResponseEntity<Object> estudianteYaPersistido(EstudiantePersistidoException ex) {
		return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage()));
	}
	
	@ExceptionHandler(FacultadInexistenteException.class)
	protected ResponseEntity<Object> facultadInexistente(FacultadInexistenteException ex) {
		return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage()));
	}
	
	@ExceptionHandler(EstudianteInexistenteException.class)
	protected ResponseEntity<Object> estudianteInexistente(EstudianteInexistenteException ex) {
		return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage()));
	}
	
	@ExceptionHandler(CarreraInexistenteException.class)
	protected ResponseEntity<Object> carreraInexistente(CarreraInexistenteException ex) {
		return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage()));
	}
	
	@ExceptionHandler(CarreraEnCursoException.class)
	protected ResponseEntity<Object> carreraEnCurso(CarreraEnCursoException ex) {
		return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage()));
	}
	
	@ExceptionHandler(MateriasEnCursoException.class)
	protected ResponseEntity<Object> materiasEnCurso(MateriasEnCursoException ex) {
		return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage()));
	}
	
	@ExceptionHandler(MateriaInexistenteException.class)
	protected ResponseEntity<Object> materiaInexistente(MateriaInexistenteException ex) {
		return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage()));
	}
	
	@ExceptionHandler(TurnoException.class)
	protected ResponseEntity<Object> turnoVacio(TurnoException ex) {
		return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage()));
	}
	
	@ExceptionHandler(TagInexistenteException.class)
	protected ResponseEntity<Object> tagInexistente(TagInexistenteException ex) {
		return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage()));
	}
	
	@ExceptionHandler(InteresYaCargadoException.class)
	protected ResponseEntity<Object> interesYaCargado(InteresYaCargadoException ex) {
		return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage()));
	}
	
	@ExceptionHandler(GrupoInexistenteException.class)
	protected ResponseEntity<Object> grupoInexistente(GrupoInexistenteException ex) {
		return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage()));
	}
	
	@ExceptionHandler(GrupoYaExisteException.class)
	protected ResponseEntity<Object> grupoYaExiste(GrupoYaExisteException ex) {
		return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage()));
	}
	
	@ExceptionHandler(SinPermisosException.class)
	protected ResponseEntity<Object> sinPermisos(SinPermisosException ex) {
		return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage()));
	}
	
	@ExceptionHandler(AuthFailedException.class)
	protected ResponseEntity<Object> authFailed(AuthFailedException ex) {
		return buildResponseEntity(new ApiError(HttpStatus.UNAUTHORIZED, ex.getMessage()));
	}

	private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
		return new ResponseEntity<>(apiError, apiError.getStatus());
	}

}
