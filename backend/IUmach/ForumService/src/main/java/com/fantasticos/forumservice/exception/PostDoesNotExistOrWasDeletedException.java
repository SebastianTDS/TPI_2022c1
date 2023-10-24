package com.fantasticos.forumservice.exception;

public class PostDoesNotExistOrWasDeletedException extends RuntimeException {
    public PostDoesNotExistOrWasDeletedException(String s) {
        super(s);
    }
}
