package com.fantasticos.algorithmservice.service;

import org.springframework.dao.TransientDataAccessResourceException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.transaction.TransactionSystemException;

import com.fantasticos.algorithmservice.dto.EstudianteDTO;
import com.fantasticos.algorithmservice.model.Estudiante;

public interface UserService {

	@Retryable(value = { TransactionSystemException.class, TransientDataAccessResourceException.class }, maxAttempts = 3, backoff = @Backoff(delay = 1000))
	Estudiante cargarEstudiante(EstudianteDTO nuevoEstudiante);

	@Retryable(value = { TransactionSystemException.class, TransientDataAccessResourceException.class }, maxAttempts = 3, backoff = @Backoff(delay = 1000))
	Estudiante cambiarFacultad(EstudianteDTO buscado);
	
	@Retryable(value = { TransactionSystemException.class, TransientDataAccessResourceException.class }, maxAttempts = 3, backoff = @Backoff(delay = 1000))
	Estudiante anotarCarrera(EstudianteDTO buscado);

	@Retryable(value = { TransactionSystemException.class, TransientDataAccessResourceException.class }, maxAttempts = 3, backoff = @Backoff(delay = 1000))
	Estudiante abandonarCarrera(EstudianteDTO buscado);

	@Retryable(value = { TransactionSystemException.class, TransientDataAccessResourceException.class }, maxAttempts = 3, backoff = @Backoff(delay = 1000))
	Estudiante anotarMateria(EstudianteDTO buscado);

	@Retryable(value = { TransactionSystemException.class, TransientDataAccessResourceException.class }, maxAttempts = 3, backoff = @Backoff(delay = 1000))
	Estudiante abandonarMateria(EstudianteDTO buscado);

	@Retryable(value = { TransactionSystemException.class, TransientDataAccessResourceException.class }, maxAttempts = 3, backoff = @Backoff(delay = 1000))
	Estudiante verEstudiante(String idEstudiante);

	@Retryable(value = { TransactionSystemException.class, TransientDataAccessResourceException.class }, maxAttempts = 3, backoff = @Backoff(delay = 1000))
	Estudiante cargarInteres(EstudianteDTO buscado);

	@Retryable(value = { TransactionSystemException.class, TransientDataAccessResourceException.class }, maxAttempts = 3, backoff = @Backoff(delay = 1000))
	Estudiante borrarInteres(EstudianteDTO buscado);

	@Retryable(value = { TransactionSystemException.class, TransientDataAccessResourceException.class }, maxAttempts = 3, backoff = @Backoff(delay = 1000))
	Estudiante sumarPuntos(EstudianteDTO buscado);


}
