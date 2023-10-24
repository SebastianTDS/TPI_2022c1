package com.fantasticos.algorithmservice.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fantasticos.algorithmservice.dto.FilterDTO;
import com.fantasticos.algorithmservice.dto.GrupoCoincidenciaDTO;


@SpringBootTest
public class AlgorithmServiceTest {

	@Autowired
	private AlgorithmService service;
	
	@Test
	public void queSePuedanListarGrupos() {
		FilterDTO dto = sinFiltros();
		
		List<GrupoCoincidenciaDTO> lista = service.buscarGrupos(dto);
		
		//assertThat(lista.size()).isGreaterThan(0);
	}
	
	@Test
	public void queSePuedanListarGruposFiltrados() {
		FilterDTO dto = filtros();
		
		List<GrupoCoincidenciaDTO> lista = service.buscarGrupos(dto);
		
//		assertThat(lista.size()).isGreaterThan(0);
//		assertThat(lista.get(0).getNameMateria()).isNotNull();
//		assertThat(lista.get(0).getCodigoMateria()).isNotNull();
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
