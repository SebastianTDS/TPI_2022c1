package com.fantasticos.algorithmservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fantasticos.algorithmservice.model.Carrera;


@Repository
public interface CarreraRepository extends Neo4jRepository<Carrera, Long> {

	@Query("MATCH (f:Facultad {abr: $abr})-->(d:Departamento)-->(c:Carrera) "
			+ "WHERE id(d) = $id "
			+ "AND (c)-->(:Materia)<--(f) "
			+ "RETURN c")
	List<Carrera> getCarrerasPorDepartamento(@Param("id") Long departamento,@Param("abr") String facultad); 
	
	Optional<Carrera> findByName(@Param("name") String name);

	@Query("MATCH (f:Facultad)-[*]->(c:Carrera) "
			+ "WHERE id(f) = $idFacultad "
			+ "AND id(c) = $idCarrera "
			+ "RETURN c")
	Optional<Carrera> findByFacultad(@Param("idCarrera") Long idCarrera, @Param("idFacultad") Long idFacultad);

	@Query("MATCH (e:Estudiante {parKey: $parKey})-->(c:Carrera) "
			+ "WHERE id(c) = $idCarrera "
			+ "RETURN c")
	Optional<Carrera> findByAlumno(@Param("idCarrera")Long idCarrera, @Param("parKey")String idAlumno);
}
