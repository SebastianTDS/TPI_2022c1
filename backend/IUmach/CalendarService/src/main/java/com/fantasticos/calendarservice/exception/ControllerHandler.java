package com.fantasticos.calendarservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ControllerHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {EventExistException.class})
    public ResponseEntity<ApiError> handleGroupNotFoundException(EventExistException e){
        HttpStatus httpStatus = HttpStatus.CONFLICT;

        ApiError apiError = new ApiError(
                e.getMessage(),
                e,
                httpStatus,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(apiError, httpStatus);
    }

    @ExceptionHandler(value = {EventNotFoundException.class})
    public ResponseEntity<ApiError> handleGroupNotFoundException(EventNotFoundException e){
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;

        ApiError apiError = new ApiError(
                e.getMessage(),
                e,
                httpStatus,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(apiError, httpStatus);
    }

}