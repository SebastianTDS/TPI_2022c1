package com.fantasticos.studentservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fantasticos.studentservice.model.HistGroup;

public interface HistGroupRepository extends JpaRepository<HistGroup, Long>{

	@Query(value ="SELECT h.*,s.*  FROM hist_group h "
			+ " JOIN hist_student s ON s.group_id = h.id"
			+ " JOIN student st ON st.id = s.student_id"
			+ " WHERE s.student_id = :idStudent"
			+ " ORDER BY h.closed DESC "
			+ " LIMIT 5", nativeQuery = true)
	public List<HistGroup> findHistGroupByIdStudent(@Param(value = "idStudent") String idStudent);
}
