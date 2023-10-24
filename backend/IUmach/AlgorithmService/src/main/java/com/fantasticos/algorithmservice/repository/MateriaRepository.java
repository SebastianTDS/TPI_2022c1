package com.fantasticos.algorithmservice.repository;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import com.fantasticos.algorithmservice.model.Materia;

@Repository
public interface MateriaRepository extends Neo4jRepository<Materia, Long> {

	@Query("MATCH (c:Carrera)-->(m:Materia) "
			+ "WHERE id(c) = $id "
			+ "AND (m)<--(:Facultad {abr: $abr}) "
			+ "RETURN m")
	List<Materia> getMateriasPorCarrera(@Param("id") Long carrera, @Param("abr") String facultad); 
	
	Optional<Materia> findByTitle(@Param("title") String title);

	
	@Query("MATCH (c:Carrera)-->(m:Materia {codigo: $codigo}) "
			+ "WHERE id(c) = $idCarrera "
			+ "RETURN m")
	Optional<Materia> findByCarrera(@Param("codigo")Integer idMateria, @Param("idCarrera")Long idCarrera);

	@Query("MATCH p = (m:Materia {codigo: $codigo})-->(t:Turno {name: $name})"
			+ "RETURN CASE WHEN count(p) > 0 THEN true ELSE false END AS disponible")
	Boolean isTurnoDisponible(@Param("codigo")Integer idMateria, @Param("name")String turno);

	Optional<Materia> findByCodigo(@Param("codigo")Integer idMateria);

	@Query("MATCH (f:Facultad {abr:$abr})-->(m:Materia {codigo:$codigo})--(t:Turno) "
			+ "RETURN t.name")
	List<String> findTurnos(@Param("abr")String facultad, @Param("codigo")Integer materia);

	@Query("MATCH (c:Carrera)-->(m:Materia)<--(e:Estudiante {parKey:$parKey})-->(c) "
			+ "WHERE id(c) = $id "
			+ "RETURN m ")
	List<Materia> materiasPend(@Param("id")Long idCarrera, @Param("parKey")String idEstudiante);
	
}
