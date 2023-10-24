package com.fantasticos.calendarservice.exception;

public class EventNotFoundException extends RuntimeException{

    public EventNotFoundException(String str) {
        super(str);
    }
}
