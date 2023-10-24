package com.example.filesservice.controller;

import com.example.filesservice.exception.ApiError;
import com.example.filesservice.exception.StudentDoestNotExistsOrWasEliminatedException;
import com.example.filesservice.exception.GroupIdInvalidOrDoesNotExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptionHandlerController {

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

}
