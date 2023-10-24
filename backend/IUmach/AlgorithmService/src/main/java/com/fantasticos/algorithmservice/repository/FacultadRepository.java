package com.fantasticos.algorithmservice.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import com.fantasticos.algorithmservice.model.Facultad;

@Repository
public interface FacultadRepository extends Neo4jRepository<Facultad, Long> {

}
