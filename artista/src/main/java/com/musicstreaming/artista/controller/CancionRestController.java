package com.musicstreaming.artista.controller;



import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.musicstreaming.artista.dto.CancionRequest;
import com.musicstreaming.artista.servicios.ServiciosCancion;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Cancion API")
@RestController
@RequestMapping("/cancion")
public class CancionRestController {

    private final ServiciosCancion serviciosCancion;

    public CancionRestController(ServiciosCancion serviciosCancion) {
        this.serviciosCancion = serviciosCancion;
    }

    @Operation(summary = "devuelve todas las canciones")
    @GetMapping("/findAll")
    public ResponseEntity<?> findAll() {
        return serviciosCancion.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getbyId(@PathVariable long id) {
        return serviciosCancion.getbyId(id);
    }

    @PostMapping("/anadir")
    public ResponseEntity<?> postCancion(@RequestBody CancionRequest input) {
        return serviciosCancion.postCancion(input);
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> updateCancion(@PathVariable Long id, @RequestBody CancionRequest inputCancion) {
        return serviciosCancion.updateCancion(id, inputCancion);
    }

    @DeleteMapping("/borrar/{id}")
    public ResponseEntity<?> deleteCancion(@PathVariable Long id) {
        return serviciosCancion.deleteCancion(id);
    }
}
