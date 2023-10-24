package com.example.notificationservice.exeptions;

public class StudentDoestNotExistsOrWasEliminatedException extends RuntimeException {

    public StudentDoestNotExistsOrWasEliminatedException(String message) {
        super(message);
    }
}
