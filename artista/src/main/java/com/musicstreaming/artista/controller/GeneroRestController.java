package com.musicstreaming.artista.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.musicstreaming.artista.dto.GeneroRequest;
import com.musicstreaming.artista.servicios.ServiciosGenero;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;


@Tag(name = "Género API")
@RestController
@RequestMapping("/genero")
public class GeneroRestController {

    private final ServiciosGenero serviciosGenero;

    public GeneroRestController(ServiciosGenero serviciosGenero) {
        this.serviciosGenero = serviciosGenero;
    }

    @Operation(summary = "Devuelve todos los géneros")
    @GetMapping("/findAll")
    public ResponseEntity<?> findAll() {
        return serviciosGenero.findAll();
    }

    @Operation(summary = "Devuelve todos las canciones de un genero")
    @GetMapping("/findAll{id}")
    public ResponseEntity<?> findAllCanciones(@PathVariable Long id) {
        return serviciosGenero.findAllCanciones(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return serviciosGenero.getById(id);
    }

    @PostMapping("/anadir")
    public ResponseEntity<?> postGenero(@Valid @RequestBody GeneroRequest input) {
        return serviciosGenero.postGenero(input);
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> updateGenero(@PathVariable Long id,
                                          @Valid @RequestBody GeneroRequest input) {
        return serviciosGenero.updateGenero(id, input);
    }

    @DeleteMapping("/borrar/{id}")
    public ResponseEntity<?> deleteGenero(@PathVariable Long id) {
        return serviciosGenero.deleteGenero(id);
    }
}
