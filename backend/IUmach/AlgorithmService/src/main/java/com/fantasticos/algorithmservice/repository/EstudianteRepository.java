package com.fantasticos.algorithmservice.repository;

import java.util.Optional;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fantasticos.algorithmservice.model.Estudiante;

@Repository
public interface EstudianteRepository extends Neo4jRepository<Estudiante, Long> {

	Optional<Estudiante> findByParKey(@Param("parKey")String parKey);

	@Query("MATCH (e:Estudiante {parKey: $parKey})-->(c:Carrera)-->(m:Materia)<--(e) "
			+ "WHERE id(c) = $idCarrera "
			+ "RETURN CASE "
			+ "	WHEN count(m) > 0 THEN false "
			+ "	ELSE true END AS result")
	Boolean isCarreraSinMaterias(@Param("parKey")String idAlumno, @Param("idCarrera") Long idCarrera);

	@Query("MATCH (e:Estudiante {parKey: $parKey})-->(m:Materia {codigo:$idMateria}) "
			+ "RETURN CASE WHEN count(e) > 0 THEN true ELSE false END AS cursando")
	Boolean isCursandoMateria(@Param("parKey")String idAlumno, @Param("idMateria")Integer idMateria);
}
