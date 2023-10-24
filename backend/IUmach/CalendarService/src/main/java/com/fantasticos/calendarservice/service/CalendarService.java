package com.fantasticos.calendarservice.service;

import com.fantasticos.calendarservice.dto.CreateEventDto;
import com.fantasticos.calendarservice.dto.EventDto;
import com.fantasticos.calendarservice.dto.EventUpdateDto;

import java.util.List;

public interface CalendarService {

    void createEvent(CreateEventDto event, String token);

    void updateEvent(EventUpdateDto event, String token);

    void deleteEvent(Long id);

    List<EventDto> findAll(Long idGroup);

    void sendEventsForReminder(String bearerToken);

    void deleteEventsForTimeExpired();
}
