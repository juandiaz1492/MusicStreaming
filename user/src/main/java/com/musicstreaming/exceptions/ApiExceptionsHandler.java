package com.musicstreaming.exceptions;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.musicstreaming.common.StandarExceptionResponse;

//manejador de excepciones

@RestControllerAdvice //manejador de excepcion en cuanto se lanze una excepción 
public class ApiExceptionsHandler {

    /*@ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleUnknownHostExceptions(Exception ex){
        StandarExceptionResponse standarExceptionResponse = new StandarExceptionResponse("tecnico", "input ouptur error", 124, "exception"); 
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(standarExceptionResponse); 
    }
        */
    
    //para tratar duplicidad en User(dni,nombre)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<StandarExceptionResponse> handleDuplicate(DataIntegrityViolationException ex) {

        StandarExceptionResponse body = new StandarExceptionResponse(
                "/errors/user/duplicate",
                "Duplicate user",
                409,
                "Ya existe un usuario con ese DNI o con ese nombre"
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
    }
}
