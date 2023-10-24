package com.fantasticos.forumservice.exception;

public class StudentDoestNotExistsOrWasEliminatedException extends RuntimeException {

    public StudentDoestNotExistsOrWasEliminatedException(String message) {
        super(message);
    }
}
