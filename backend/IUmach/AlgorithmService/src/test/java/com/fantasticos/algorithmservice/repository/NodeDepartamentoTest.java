package com.fantasticos.algorithmservice.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.neo4j.DataNeo4jTest;

@DataNeo4jTest
public class NodeDepartamentoTest {

	@Autowired
	private DepartamentoRepository repo;
	
	@Test
	public void queSePuedanListarDepartamentosDeFacultad() {
		String abrFacultad = "UNLAM";
		
		assertThat(repo.findByFacultad(abrFacultad)).isNotEmpty();
	}
}
