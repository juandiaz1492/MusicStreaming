package com.musicstreaming.exceptions;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.musicstreaming.common.StandarExceptionResponse;

//manejador de excepciones

@RestControllerAdvice //manejador de excepcion en cuanto se lanze una excepción 
public class ApiExceptionsHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleUnknownHostExceptions(Exception ex){
        StandarExceptionResponse standarExceptionResponse = new StandarExceptionResponse("tecnico", "input ouptur error", 124, "exception"); 
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(standarExceptionResponse); 
    }

}
