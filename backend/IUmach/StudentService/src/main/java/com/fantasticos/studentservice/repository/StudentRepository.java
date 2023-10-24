package com.fantasticos.studentservice.repository;

import com.fantasticos.studentservice.model.Student;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student,String> {

	Optional<Student> findByEmail(String email);

}
