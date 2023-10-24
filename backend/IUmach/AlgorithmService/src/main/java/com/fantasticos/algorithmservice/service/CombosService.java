package com.fantasticos.algorithmservice.service;

import java.util.List;

import org.springframework.dao.TransientDataAccessResourceException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.transaction.TransactionSystemException;

import com.fantasticos.algorithmservice.model.Carrera;
import com.fantasticos.algorithmservice.model.Departamento;
import com.fantasticos.algorithmservice.model.Facultad;
import com.fantasticos.algorithmservice.model.Materia;
import com.fantasticos.algorithmservice.model.Tag;

public interface CombosService {

	@Retryable(value = { TransactionSystemException.class, TransientDataAccessResourceException.class }, maxAttempts = 3, backoff = @Backoff(delay = 1000))
	List<Facultad> getFacultades();

	@Retryable(value = { TransactionSystemException.class, TransientDataAccessResourceException.class }, maxAttempts = 3, backoff = @Backoff(delay = 1000))
	List<Departamento> getDepartamentos(String facultad);

	@Retryable(value = { TransactionSystemException.class, TransientDataAccessResourceException.class }, maxAttempts = 3, backoff = @Backoff(delay = 1000))
	List<Carrera> getCarreras(String facultad, Long departamento);

	@Retryable(value = { TransactionSystemException.class, TransientDataAccessResourceException.class }, maxAttempts = 3, backoff = @Backoff(delay = 1000))
	List<Materia> getMaterias(String facultad, Long carrera);

	@Retryable(value = { TransactionSystemException.class, TransientDataAccessResourceException.class }, maxAttempts = 3, backoff = @Backoff(delay = 1000))
	List<Tag> getTags();

	@Retryable(value = { TransactionSystemException.class, TransientDataAccessResourceException.class }, maxAttempts = 3, backoff = @Backoff(delay = 1000))
	public List<String> getTurnosDisp(String facultad, Integer materia);

	
}
