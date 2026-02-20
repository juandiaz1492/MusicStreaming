package com.musicstreaming.artista.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.musicstreaming.artista.common.StandarExceptionResponse;

//manejador de excepciones

@RestControllerAdvice // manejador de excepcion en cuanto se lanze una excepción
public class ApiExceptionsHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleUnknownHostExceptions(Exception ex) {
        StandarExceptionResponse standarExceptionResponse = new StandarExceptionResponse("tecnico",
                "input ouptur error", 124, "exception");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(standarExceptionResponse);
    }
    /*
     * @ExceptionHandler(WebClientResponseException.NotFound.class)
     * public ResponseEntity<StandarExceptionResponse>
     * handleUserNotFound(WebClientResponseException.NotFound ex) {
     * 
     * StandarExceptionResponse response = new StandarExceptionResponse(
     * "/errors/user/not-found",
     * "User not found",
     * 2345,
     * "El usuario no existe"
     * );
     * response.setInstance("/errors/user/not-found/01");
     * 
     * return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
     * }
     */

    // para tratar duplicidad en Artista(nombre)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<StandarExceptionResponse> handleDuplicate(DataIntegrityViolationException ex) {

        StandarExceptionResponse body = new StandarExceptionResponse(
                "/errors/user/duplicate",
                "Duplicate user",
                409,
                "Ya existe un artista con ese nombre");

        return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleJsonParse(HttpMessageNotReadableException ex) {

        Throwable cause = ex.getCause();
        if (cause instanceof InvalidFormatException ife) {
            String field = ife.getPath().isEmpty() ? "body" : ife.getPath().get(0).getFieldName();

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "error", "BAD_REQUEST",
                    "message", "Formato inválido en '" + field + "'. Usa yyyy-MM-dd",
                    "value", String.valueOf(ife.getValue())));
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                "error", "BAD_REQUEST",
                "message", "JSON inválido o mal formado"));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

        Map<String, Object> body = new HashMap<>();
        body.put("type", "validation");
        body.put("title", "Validation error");
        body.put("code", 400);
        body.put("errors", errors);

        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(DuplicateAlbumException.class)
    public ResponseEntity<StandarExceptionResponse> handleDuplicateAlbum(DuplicateAlbumException ex) {

        StandarExceptionResponse body = new StandarExceptionResponse(
                "/errors/album/duplicate",
                "Duplicate album",
                409,
                ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
    }

    @ExceptionHandler(AlbumNotFoundException.class)
    public ResponseEntity<StandarExceptionResponse> handleAlbumNotFound(AlbumNotFoundException ex) {

        StandarExceptionResponse body = new StandarExceptionResponse(
                "/errors/album/not-found",
                "Album not found",
                404,
                ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler(DuplicateCancionException.class)
    public ResponseEntity<StandarExceptionResponse> handleDuplicateCancion(DuplicateCancionException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                new StandarExceptionResponse(
                        "/errors/cancion/duplicate",
                        "Duplicate song",
                        409,
                        ex.getMessage()));
    }

    @ExceptionHandler(DuplicateGeneroException.class)
    public ResponseEntity<StandarExceptionResponse> handleDuplicateGenero(DuplicateGeneroException ex) {

        StandarExceptionResponse body = new StandarExceptionResponse(
                "/errors/genero/duplicate",
                "Duplicate genero",
                409,
                ex.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
    }

}
