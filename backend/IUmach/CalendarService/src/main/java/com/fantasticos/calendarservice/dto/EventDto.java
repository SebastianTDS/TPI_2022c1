package com.fantasticos.calendarservice.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class EventDto {

    private Long id;
    private LocalDate eventDate;
    private String name;
    private String description;
    private Long idGroup;
}
