package com.fantasticos.algorithmservice.repository;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.fantasticos.algorithmservice.dto.FilterDTO;

public interface AlgorithmRepository {

	public Page<Map<String, Object>> getGruposByCoincidence(FilterDTO busqueda, PageRequest page);
	public Page<Map<String, Object>> getEstudiantesByCoincidence(FilterDTO busqueda, PageRequest page);
}
