package com.fantasticos.algorithmservice.repository;

import java.util.List;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fantasticos.algorithmservice.model.Departamento;

@Repository
public interface DepartamentoRepository extends Neo4jRepository<Departamento, Long> {

	@Query("MATCH (:Facultad {abr: $facultad})-->(d:Departamento) "
			+ "RETURN d")
    List<Departamento> findByFacultad(@Param("facultad")String facultad);

	
	
}
