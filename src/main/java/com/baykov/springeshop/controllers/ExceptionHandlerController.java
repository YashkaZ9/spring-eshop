package com.baykov.springeshop.controllers;

import com.baykov.springeshop.exceptions.ExceptionResponse;
import com.baykov.springeshop.exceptions.PersonNotCreatedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ExceptionHandlerController {
    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handlePersonException(PersonNotCreatedException e) {
        ExceptionResponse response = new ExceptionResponse(e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
