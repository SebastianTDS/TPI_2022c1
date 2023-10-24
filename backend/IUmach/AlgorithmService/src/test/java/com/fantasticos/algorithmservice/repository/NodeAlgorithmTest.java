package com.fantasticos.algorithmservice.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.fantasticos.algorithmservice.dto.FilterDTO;


@SpringBootTest
public class NodeAlgorithmTest {

	@Autowired
	private AlgorithmRepository repoAlgoritmo;

	@Test
	public void queSeListenGruposParaUsuario() {
		FilterDTO dto = sinFiltros();
		
		Page<Map<String, Object>> parsed = repoAlgoritmo.getGruposByCoincidence(dto, PageRequest.of(dto.getPagina(), 1));
		
		assertThat(parsed).hasSize(1);
		assertThat(parsed.getTotalElements()).isGreaterThan(1);
		assertThat(parsed.getNumberOfElements()).isEqualTo(1);
	}
	
	@Test
	public void queSeListenGruposFiltradosParaUsuario() {
		FilterDTO dto = filtros();
		
		Page<Map<String, Object>> parsed = repoAlgoritmo.getGruposByCoincidence(dto, PageRequest.of(dto.getPagina(), 1));
		
		assertThat(parsed).hasSize(1);
		assertThat(parsed.getTotalElements()).isGreaterThan(0);
		assertThat(parsed.getNumberOfElements()).isEqualTo(1);
	}

	private FilterDTO filtros() {
		FilterDTO dto = new FilterDTO();
		
		dto.setUser("eddc430d-14b7-4141-af53-e73cfb089197");
		dto.setMateria(2619);
		dto.setPagina(0);
		
		return dto;
	}
	
	private FilterDTO sinFiltros() {
		FilterDTO dto = new FilterDTO();
		
		dto.setUser("eddc430d-14b7-4141-af53-e73cfb089197");
		dto.setPagina(0);
		
		return dto;
	}
}
