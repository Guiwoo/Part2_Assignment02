package com.guiwoo.weather.config;

import com.guiwoo.weather.error.DateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DateException.class)
    public ResponseEntity<?> dateException(DateException e) {
        return ResponseEntity.status(e.getErrorCode().getHttpStatus()).body(e.getErrorMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public Exception handlerException(){
        System.out.println("Error  from Global Exception occur");
        return new Exception();
    }
}