package com.fantasticos.calendarservice.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
public class EventUpdateDto extends CreateEventDto{
    private Long id;
}
