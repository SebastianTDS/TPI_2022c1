package com.fantasticos.studentservice.repository;

import com.fantasticos.studentservice.model.GroupEntity;
import com.fantasticos.studentservice.model.RequestJoin;
import com.fantasticos.studentservice.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RequestJoinRepository extends JpaRepository<RequestJoin, Long> {

    Optional<RequestJoin> findByStudentAndGroup(Student student, GroupEntity group);

    Optional<RequestJoin> findByIdNotAndStudentAndGroupAndAccepted(Long id,
                                                              Student dtoStudentToStudent,
                                                              GroupEntity groupDtoToGroupEntity,
                                                              Boolean accepted);
}
