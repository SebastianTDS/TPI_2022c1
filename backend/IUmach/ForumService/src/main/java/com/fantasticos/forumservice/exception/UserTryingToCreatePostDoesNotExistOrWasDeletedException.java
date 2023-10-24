package com.fantasticos.forumservice.exception;

public class UserTryingToCreatePostDoesNotExistOrWasDeletedException extends RuntimeException {
    public UserTryingToCreatePostDoesNotExistOrWasDeletedException(String s) {
        super(s);
    }
}
