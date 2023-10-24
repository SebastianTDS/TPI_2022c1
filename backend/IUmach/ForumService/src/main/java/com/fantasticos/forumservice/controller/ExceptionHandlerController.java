package com.fantasticos.forumservice.controller;

import com.fantasticos.forumservice.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptionHandlerController {


    @ExceptionHandler(value = {CommentDoesNotExistOrWasDeletedException.class})
    public ResponseEntity<ApiError> handleCommentDoesNotExistOrWasDeletedException(CommentDoesNotExistOrWasDeletedException e) {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;

        ApiError apiError = new ApiError(
                e.getMessage(),
                httpStatus,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(apiError, httpStatus);
    }

    @ExceptionHandler(value = {GroupIdInvalidOrDoesNotExistException.class})
    public ResponseEntity<ApiError> handleGroupIdInvalidOrDoesNotExistException(GroupIdInvalidOrDoesNotExistException e) {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;

        ApiError apiError = new ApiError(
                e.getMessage(),
                httpStatus,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(apiError, httpStatus);
    }

    @ExceptionHandler(value = {IdFieldForPostOrCommentIsEmptyException.class})
    public ResponseEntity<ApiError> handleIdFieldForPostOrCommentIsEmptyException(IdFieldForPostOrCommentIsEmptyException e) {
        HttpStatus httpStatus = HttpStatus.CONFLICT;

        ApiError apiError = new ApiError(
                e.getMessage(),
                httpStatus,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(apiError, httpStatus);
    }

    @ExceptionHandler(value = {PostDoesNotExistOrWasDeletedException.class})
    public ResponseEntity<ApiError> handlePostDoesNotExistOrWasDeletedException(PostDoesNotExistOrWasDeletedException e) {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;

        ApiError apiError = new ApiError(
                e.getMessage(),
                httpStatus,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(apiError, httpStatus);
    }

    @ExceptionHandler(value = {StudentDoestNotExistsOrWasEliminatedException.class})
    public ResponseEntity<ApiError> handleStudentDoestNotExistsOrWasEliminatedException(StudentDoestNotExistsOrWasEliminatedException e) {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;

        ApiError apiError = new ApiError(
                e.getMessage(),
                httpStatus,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(apiError, httpStatus);
    }

    @ExceptionHandler(value = {UserTryingToCreatePostDoesNotExistOrWasDeletedException.class})
    public ResponseEntity<ApiError> handleUserTryingToCreatePostDoesNotExistOrWasDeletedException(UserTryingToCreatePostDoesNotExistOrWasDeletedException e) {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;

        ApiError apiError = new ApiError(
                e.getMessage(),
                httpStatus,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(apiError, httpStatus);
    }


}
