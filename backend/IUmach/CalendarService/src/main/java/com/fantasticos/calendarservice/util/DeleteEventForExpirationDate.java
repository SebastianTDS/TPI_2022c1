package com.fantasticos.calendarservice.util;

import com.fantasticos.calendarservice.service.CalendarService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteEventForExpirationDate {

    private final CalendarService service;

    @Scheduled(cron = "0 0 12 * * ?")
    public void deleteEventsForTimeExpired() {
        service.deleteEventsForTimeExpired();
    }
}
