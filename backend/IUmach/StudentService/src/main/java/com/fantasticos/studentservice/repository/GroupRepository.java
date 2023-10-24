package com.fantasticos.studentservice.repository;

import com.fantasticos.studentservice.model.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;

public interface GroupRepository extends JpaRepository<GroupEntity, Long> {

    Optional<GroupEntity> findByName(String name);

    Optional<GroupEntity> findByDescription(String description);

    Optional<GroupEntity> findByCareer(String career);

    Optional<GroupEntity> findBySubject(String subject);

    Optional<GroupEntity> findByIdNotAndNameAndDescriptionAndCareerAndSubject(Long id,
                                                                              String name,
                                                                              String description,
                                                                              String career,
                                                                              String subject);

    Optional<GroupEntity> findByNameAndDescriptionAndCareerAndSubject(String name,
                                                                      String description,
                                                                      String career,
                                                                      String subject);

    @Query(value = "SELECT g.* FROM group_entity g "
    		+ "INNER JOIN group_student gs ON g.id = gs.group_id "
    		+ "INNER JOIN student s ON gs.student_id = s.id "
    		+ "WHERE s.id = :idUser"
    		, nativeQuery = true)
	Set<GroupEntity> getByStudent(@Param("idUser") String idUser);
}