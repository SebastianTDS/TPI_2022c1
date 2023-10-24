package com.fantasticos.calendarservice.repository;

import com.fantasticos.calendarservice.model.CalendarEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface CalendarRepository extends JpaRepository<CalendarEntity, Long> {

    Optional<CalendarEntity> findByNameAndDescription(String name, String description);

    Optional<CalendarEntity> findByIdNotAndNameAndDescription(Long id, String name, String description);

    @Query(
            value = "SELECT C.* FROM CALENDAR_ENTITY C WHERE DATE_SUB(EVENT_DATE, INTERVAL 1 DAY) <= CURDATE()",
            nativeQuery = true)
    List<CalendarEntity> findAllEventsForReminder();

    List<CalendarEntity> findAllByIdGroupOrderByEventDate(Long idGroup);

    @Transactional
    @Query(value = "DELETE FROM CALENDAR_ENTITY WHERE EVENT_DATE < CURDATE()",
            nativeQuery = true)
    @Modifying
    void deleteEventsForTimeExpired();
}
