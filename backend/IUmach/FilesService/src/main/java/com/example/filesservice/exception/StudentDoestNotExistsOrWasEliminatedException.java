package com.example.filesservice.exception;

public class StudentDoestNotExistsOrWasEliminatedException extends RuntimeException {
    public StudentDoestNotExistsOrWasEliminatedException(String s) {
        super(s);
    }
}
