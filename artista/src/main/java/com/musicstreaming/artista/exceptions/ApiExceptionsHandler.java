package com.musicstreaming.artista.exceptions; 


import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import com.musicstreaming.artista.common.StandarExceptionResponse;

//manejador de excepciones

@RestControllerAdvice //manejador de excepcion en cuanto se lanze una excepción 
public class ApiExceptionsHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleUnknownHostExceptions(Exception ex){
        StandarExceptionResponse standarExceptionResponse = new StandarExceptionResponse("tecnico", "input ouptur error", 124, "exception"); 
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(standarExceptionResponse); 
    }
    /* 
    @ExceptionHandler(WebClientResponseException.NotFound.class)
    public ResponseEntity<StandarExceptionResponse> handleUserNotFound(WebClientResponseException.NotFound ex) {

        StandarExceptionResponse response = new StandarExceptionResponse(
            "/errors/user/not-found",
            "User not found",
            2345,
            "El usuario no existe"
        );
        response.setInstance("/errors/user/not-found/01");

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
       }
       */

    //para tratar duplicidad en Artista(nombre)
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
