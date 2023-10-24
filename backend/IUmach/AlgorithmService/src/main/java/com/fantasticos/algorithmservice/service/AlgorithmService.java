package com.fantasticos.algorithmservice.service;

import java.util.List;

import org.springframework.retry.annotation.Retryable;
import org.springframework.dao.TransientDataAccessResourceException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.transaction.TransactionSystemException;

import com.fantasticos.algorithmservice.dto.EstudianteCoincidenciaDTO;
import com.fantasticos.algorithmservice.dto.FilterDTO;
import com.fantasticos.algorithmservice.dto.GrupoCoincidenciaDTO;

public interface AlgorithmService {
	
	@Retryable(value = { TransactionSystemException.class, TransientDataAccessResourceException.class }, maxAttempts = 3, backoff = @Backoff(delay = 1000))
	public List<GrupoCoincidenciaDTO> buscarGrupos(FilterDTO busqueda);
	@Retryable(value = { TransactionSystemException.class, TransientDataAccessResourceException.class }, maxAttempts = 3, backoff = @Backoff(delay = 1000))
	public List<EstudianteCoincidenciaDTO> buscarEstudiantes(FilterDTO busqueda);
	
}
