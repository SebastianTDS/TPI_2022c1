package com.fantasticos.calendarservice.controller;

import com.fantasticos.calendarservice.dto.CreateEventDto;
import com.fantasticos.calendarservice.dto.EventDeleteDto;
import com.fantasticos.calendarservice.dto.EventDto;
import com.fantasticos.calendarservice.dto.EventUpdateDto;
import com.fantasticos.calendarservice.service.CalendarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/calendar")
@RequiredArgsConstructor
public class CalendarController {

    private final CalendarService service;


    @GetMapping("/{idGroup}")
    public ResponseEntity<List<EventDto>> findAll(@PathVariable("idGroup") Long idGroup){
        return new ResponseEntity<>(service.findAll(idGroup), HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody CreateEventDto event, @RequestHeader("Authorization") String bearerToken){
        service.createEvent(event, bearerToken);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@RequestBody EventUpdateDto event, @RequestHeader("Authorization") String bearerToken){
        service.updateEvent(event,bearerToken);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestBody EventDeleteDto eventDeleteDto){
        service.deleteEvent(eventDeleteDto.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/events/reminder")
    public void eventsToSend(@RequestHeader("Authorization") String bearerToken){
        service.sendEventsForReminder(bearerToken);
    }
}
