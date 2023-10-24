package com.example.notificationservice.controller;

import com.example.notificationservice.exeptions.ApiError;
import com.example.notificationservice.exeptions.GroupIdInvalidOrDoesNotExistException;
import com.example.notificationservice.exeptions.StudentDoestNotExistsOrWasEliminatedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptionHandlerController {


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




}
