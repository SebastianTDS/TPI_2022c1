package com.fantasticos.studentservice.repository;

import com.fantasticos.studentservice.model.ValorationNotification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ValorationRepository extends JpaRepository<ValorationNotification,Long> {

    List<ValorationNotification> findAllByidValuationOut(String userName);
}
