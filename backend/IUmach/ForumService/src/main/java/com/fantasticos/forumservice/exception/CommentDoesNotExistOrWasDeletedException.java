package com.fantasticos.forumservice.exception;

public class CommentDoesNotExistOrWasDeletedException extends RuntimeException{
    public CommentDoesNotExistOrWasDeletedException(String s) {
        super(s);
    }
}
