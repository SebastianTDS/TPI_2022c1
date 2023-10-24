package com.fantasticos.algorithmservice.repository;

import java.util.Optional;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fantasticos.algorithmservice.model.Grupo;

@Repository
public interface GrupoRepository extends Neo4jRepository<Grupo, Long> {

	Optional<Grupo> findByParKey(@Param("parKey") Long idGrupo);


}
