package com.fantasticos.algorithmservice.service;

import org.springframework.dao.TransientDataAccessResourceException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.transaction.TransactionSystemException;

import com.fantasticos.algorithmservice.dto.GrupoDTO;
import com.fantasticos.algorithmservice.model.Grupo;

public interface GrupoService {

	@Retryable(value = { TransactionSystemException.class, TransientDataAccessResourceException.class }, maxAttempts = 3, backoff = @Backoff(delay = 1000))
	Grupo cargarGrupo(GrupoDTO nuevo);

	@Retryable(value = { TransactionSystemException.class, TransientDataAccessResourceException.class }, maxAttempts = 3, backoff = @Backoff(delay = 1000))
	Grupo cargarInteres(GrupoDTO objetivo);

	@Retryable(value = { TransactionSystemException.class, TransientDataAccessResourceException.class }, maxAttempts = 3, backoff = @Backoff(delay = 1000))
	Grupo borrarInteres(GrupoDTO objetivo);

	@Retryable(value = { TransactionSystemException.class, TransientDataAccessResourceException.class }, maxAttempts = 3, backoff = @Backoff(delay = 1000))
	Grupo buscarGrupo(Long idGrupo);

	@Retryable(value = { TransactionSystemException.class, TransientDataAccessResourceException.class }, maxAttempts = 3, backoff = @Backoff(delay = 1000))
	Grupo agregarMiembro(GrupoDTO objetivo);

	@Retryable(value = { TransactionSystemException.class, TransientDataAccessResourceException.class }, maxAttempts = 3, backoff = @Backoff(delay = 1000))
	void borrarGrupo(GrupoDTO objetivo);

	@Retryable(value = { TransactionSystemException.class, TransientDataAccessResourceException.class }, maxAttempts = 3, backoff = @Backoff(delay = 1000))
	Grupo removerMiembro(GrupoDTO objetivo);

}
