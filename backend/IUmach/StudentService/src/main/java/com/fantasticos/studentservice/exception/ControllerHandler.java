package com.fantasticos.studentservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ControllerHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {GroupNotFoundException.class})
    public ResponseEntity<ApiError> handleGroupNotFoundException(GroupNotFoundException e){
        HttpStatus httpStatus = HttpStatus.CONFLICT;

        ApiError apiError = new ApiError(
                e.getMessage(),
                e,
                httpStatus,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(apiError, httpStatus);
    }

    @ExceptionHandler(value = {GroupExistException.class})
    public ResponseEntity<ApiError> handleGroupExistException(GroupExistException e){
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        ApiError apiError = new ApiError(
                e.getMessage(),
                e,
                httpStatus,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(apiError, httpStatus);
    }

    @ExceptionHandler(value = {GroupJoinException.class})
    public ResponseEntity<ApiError> handleGroupJoinException(GroupJoinException e){
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        ApiError apiError = new ApiError(
                e.getMessage(),
                e,
                httpStatus,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(apiError, httpStatus);
    }
    @ExceptionHandler(value = {NotFounStudentException.class})
    public ResponseEntity<ApiError> notFounStudentException(NotFounStudentException e){
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;

        ApiError apiError = new ApiError(
                e.getMessage(),
                e,
                httpStatus,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(apiError, httpStatus);
    }
    @ExceptionHandler(value = {RequestJoinNotFoundException.class})
    public ResponseEntity<ApiError> notFounStudentException(RequestJoinNotFoundException e){
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
