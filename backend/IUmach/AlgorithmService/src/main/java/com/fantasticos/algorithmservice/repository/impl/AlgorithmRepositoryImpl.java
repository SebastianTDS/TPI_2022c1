package com.fantasticos.algorithmservice.repository.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Repository;

import com.fantasticos.algorithmservice.dto.FilterDTO;
import com.fantasticos.algorithmservice.repository.AlgorithmRepository;


@Repository
public class AlgorithmRepositoryImpl implements AlgorithmRepository{

	private Neo4jClient client;
	
	public AlgorithmRepositoryImpl() {
		Driver driver = GraphDatabase.driver("neo4j+s://442d07e6.databases.neo4j.io",
				AuthTokens.basic("neo4j", "T0pbSvqY8IDUs3hTTVUk1K3EF67oYPC4T97jZYuU1cI"));
		
		this.client = Neo4jClient.create(driver);
	}
	
	@Override
	public Page<Map<String, Object>> getGruposByCoincidence(FilterDTO busqueda, PageRequest page) {
		return generatePage(busqueda, page);
	}
	

	private Page<Map<String, Object>> generatePage(FilterDTO busqueda, Pageable page) {
		String query = "CALL{ "
				+ "    MATCH (e1:Estudiante{parKey:$parKey})-->(tag:Tag)<--(g:Grupo)<-[:INTEGRA]-(e2:Estudiante) "
				+ "    WHERE NOT (g)<-[:INTEGRA]-(e1) "
				+ "    RETURN g AS grupo, e1.puntos as puntosA, avg(e2.puntos) as puntosB, 1 as orden "
				+ "    UNION "
				+ "    MATCH (e1:Estudiante{parKey:$parKey})-->(m:Materia)<--(g:Grupo)<-[:INTEGRA]-(e2:Estudiante) "
				+ "    WHERE NOT (g)<-[:INTEGRA]-(e1) "
				+ "    RETURN g AS grupo, e1.puntos as puntosA, avg(e2.puntos) as puntosB, 1 as orden  "
				+ "    UNION "
				+ "    MATCH (e1:Estudiante{parKey:$parKey}), (g:Grupo)<-[:INTEGRA]-(e2:Estudiante) "
				+ "    WHERE NOT (g)<-[:INTEGRA]-(e1) "
				+ "    AND NOT (e1)-->(:Materia)<--(g) "
				+ "    AND NOT (e1)-->(:Tag)<--(g) "
				+ "    RETURN g AS grupo , e1.puntos as puntosA, avg(e2.puntos) as puntosB , 0 as orden "
				+ "} "
				+ "WITH grupo, puntosA, puntosB, orden, round(100 - 100 * abs(1/(1 + 10^((puntosA - puntosB)/400)) - 1/(1 + 10^((puntosB - puntosA)/400))), 1) AS compatibilidad "
				+ "MATCH (grupo) "
				+ "WHERE ($tag IS NULL OR EXISTS{ MATCH (grupo)-->(t1:Tag) WHERE id(t1) = $tag }) "
				+ "AND ($materia IS NULL OR EXISTS{ MATCH (grupo)-->(m1:Materia{codigo:$materia}) }) "
				+ "OPTIONAL MATCH (grupo:Grupo)-->(m:Materia) "
				+ "OPTIONAL MATCH (grupo:Grupo)-->(t:Tag) "
				+ "RETURN grupo, orden, collect(t) as tags, m as materia, compatibilidad "
				+ "ORDER BY orden DESC, compatibilidad DESC "
				+ "SKIP $skip LIMIT $limit";
		
		String queryCount = "MATCH (e1:Estudiante{parKey:$parKey}), (g:Grupo) "
				+ "WHERE NOT (g)<-[:INTEGRA]-(e1) "
				+ "AND ($tag IS NULL OR EXISTS{ MATCH (g)-->(t1:Tag) WHERE id(t1) = $tag }) "
				+ "AND ($materia IS NULL OR EXISTS{ MATCH (g)-->(m1:Materia{codigo:$materia}) }) "
				+ "RETURN count(g) AS total";
		
		Collection<Map<String, Object>> lista = client.query(query)
				.in("neo4j")
				.bind(busqueda.getUser()).to("parKey")
				.bind(page.getPageSize()).to("limit")
				.bind(page.getOffset()).to("skip")
				.bind(busqueda.getTag()).to("tag")
				.bind(busqueda.getMateria()).to("materia")
				.fetch()
				.all();
		
		Long total = (Long) client.query(queryCount)
				.in("neo4j")
				.bind(busqueda.getUser()).to("parKey")
				.bind(busqueda.getTag()).to("tag")
				.bind(busqueda.getMateria()).to("materia")
				.fetch().one().get().get("total");
		
		return new PageImpl<Map<String, Object>>(new ArrayList<Map<String, Object>>(lista), page, total);
	}

	@Override
	public Page<Map<String, Object>> getEstudiantesByCoincidence(FilterDTO busqueda, PageRequest page) {
		String query = "CALL{ "
				+ "	MATCH (e2:Estudiante)-[:INTEGRA]->(g:Grupo{parKey:$parKey})-->(tag:Tag)<--(e1:Estudiante) "
				+ "	WHERE NOT (g)<-[:INTEGRA]-(e1) "
				+ "	RETURN e1 AS estudiante, e1.puntos as puntosA, avg(e2.puntos) as puntosB, 1 as orden "
				+ "	UNION "
				+ "	MATCH (e2:Estudiante)-[:INTEGRA]->(g:Grupo{parKey:$parKey})-->(m:Materia)<--(e1:Estudiante) "
				+ "	WHERE NOT (g)<-[:INTEGRA]-(e1) "
				+ "	RETURN e1 AS estudiante, e1.puntos as puntosA, avg(e2.puntos) as puntosB, 1 as orden "
				+ "	UNION "
				+ "	MATCH (e1:Estudiante), (g:Grupo{parKey:$parKey})<-[:INTEGRA]-(e2:Estudiante) "
				+ "	WHERE NOT (g)<-[:INTEGRA]-(e1) "
				+ "	AND NOT (e1)-->(:Materia)<--(g) "
				+ "	AND NOT (e1)-->(:Tag)<--(g) "
				+ "	RETURN e1 AS estudiante , e1.puntos as puntosA, avg(e2.puntos) as puntosB , 0 as orden "
				+ "} "
				+ "WITH estudiante, puntosA, puntosB, orden, round(100 - 100 * abs(1/(1 + 10^((puntosA - puntosB)/400)) - 1/(1 + 10^((puntosB - puntosA)/400))), 1) AS compatibilidad "
				+ "MATCH (estudiante) "
				+ "WHERE ($tag IS NULL OR EXISTS{ MATCH (estudiante)-->(t1:Tag) WHERE id(t1) = $tag }) "
				+ "AND ($materia IS NULL OR EXISTS{ MATCH (estudiante)-->(m1:Materia{codigo:$materia}) }) "
				+ "OPTIONAL MATCH (estudiante:Estudiante)-->(m:Materia) "
				+ "OPTIONAL MATCH (estudiante:Estudiante)-->(c:Carrera) "
				+ "OPTIONAL MATCH (estudiante:Estudiante)-->(f:Facultad) "
				+ "OPTIONAL MATCH (estudiante:Estudiante)-->(t:Tag) "
				+ "RETURN estudiante, orden, collect(t) as tags, collect(m) as materias, collect(c) as carreras, f as facultad, compatibilidad "
				+ "ORDER BY orden DESC, compatibilidad DESC "
				+ "SKIP $skip LIMIT $limit";
		
		String queryCount = "MATCH (e1:Estudiante), (g:Grupo{parKey:$parKey}) "
				+ "WHERE NOT (g)<-[:INTEGRA]-(e1) "
				+ "AND ($tag IS NULL OR EXISTS{ MATCH (g)-->(t1:Tag) WHERE id(t1) = $tag }) "
				+ "AND ($materia IS NULL OR EXISTS{ MATCH (g)-->(m1:Materia{codigo:$materia}) }) "
				+ "RETURN count(e1) AS total";
		
		Collection<Map<String, Object>> lista = client.query(query)
				.in("neo4j")
				.bind(busqueda.getGroup()).to("parKey")
				.bind(page.getPageSize()).to("limit")
				.bind(page.getOffset()).to("skip")
				.bind(busqueda.getTag()).to("tag")
				.bind(busqueda.getMateria()).to("materia")
				.fetch()
				.all();
		
		Long total = (Long) client.query(queryCount)
				.in("neo4j")
				.bind(busqueda.getGroup()).to("parKey")
				.bind(busqueda.getTag()).to("tag")
				.bind(busqueda.getMateria()).to("materia")
				.fetch().one().get().get("total");
		
		return new PageImpl<Map<String, Object>>(new ArrayList<Map<String, Object>>(lista), page, total);
	}


}
