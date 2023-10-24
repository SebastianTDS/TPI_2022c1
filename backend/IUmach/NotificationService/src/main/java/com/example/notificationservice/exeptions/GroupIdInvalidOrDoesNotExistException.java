package com.example.notificationservice.exeptions;

public class GroupIdInvalidOrDoesNotExistException extends RuntimeException {
    public GroupIdInvalidOrDoesNotExistException(String s) {
        super(s);
    }
}
