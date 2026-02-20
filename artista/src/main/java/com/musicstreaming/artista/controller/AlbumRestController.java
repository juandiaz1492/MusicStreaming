package com.musicstreaming.artista.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.musicstreaming.artista.dto.AlbumRequest;

import com.musicstreaming.artista.servicios.ServiciosAlbum;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Album API")
@RestController
@RequestMapping("/album")
public class AlbumRestController {

    private final ServiciosAlbum serviciosAlbum;

    public AlbumRestController(ServiciosAlbum serviciosAlbum) {
        this.serviciosAlbum = serviciosAlbum;
    }

    @Operation(summary = "devuelve todos los álbumes")
    @GetMapping("/findAll")
    public ResponseEntity<?> findAll() {
        return serviciosAlbum.findAll();
    }

    @Operation(summary = "devuelve todos las canciones de un album")
    @GetMapping("/findAll/{id}")
    public ResponseEntity<?> findAllAlbum(@PathVariable long id) {
        return serviciosAlbum.findAllAlbum(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getbyId(@PathVariable long id) {
        return serviciosAlbum.getbyId(id);
    }

    @PostMapping("/anadir")
    public ResponseEntity<?> postAlbum(@Valid @RequestBody AlbumRequest input) {
        return serviciosAlbum.postAlbum(input);
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> updateAlbum(@PathVariable Long id, @Valid @RequestBody AlbumRequest inputAlbum) {
        return serviciosAlbum.updateAlbum(id, inputAlbum);
    }

    @DeleteMapping("/borrar/{id}")
    public ResponseEntity<?> deleteAlbum(@PathVariable Long id) {
        return serviciosAlbum.deleteAlbum(id);
    }
}
