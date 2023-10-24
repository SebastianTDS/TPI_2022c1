package com.fantasticos.algorithmservice.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;

import com.fantasticos.algorithmservice.model.Tag;

public interface TagRepository extends Neo4jRepository<Tag, Long> {

}
