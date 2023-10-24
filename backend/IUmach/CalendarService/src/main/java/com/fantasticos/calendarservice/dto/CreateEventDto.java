package com.fantasticos.calendarservice.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateEventDto {

    private LocalDate eventDate;
    private String name;
    private String description;
    private Long idGroup;

}
