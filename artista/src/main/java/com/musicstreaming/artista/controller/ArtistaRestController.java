package com.musicstreaming.artista.controller;


import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;


import com.musicstreaming.artista.dto.ArtistRequest;
import com.musicstreaming.artista.servicios.ServiciosArtista;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name= "Artista API")
@RestController
@RequestMapping("/artista")
public class ArtistaRestController {

    private final ServiciosArtista serviciosArtista;

    public ArtistaRestController(ServiciosArtista serviciosArtista) {
        this.serviciosArtista = serviciosArtista;
    }
    
    @Operation(summary = "devuelve todos los artistas")
    @GetMapping("/findAll")
    public ResponseEntity<?> findAll() {
        return serviciosArtista.findAll(); 
    }

    @Operation(summary = "devuelve todos los artistas")
    @GetMapping("/findAll/{id}")
    public ResponseEntity<?> findAllArtista(@PathVariable long id) {
        return serviciosArtista.findAllArtista(id); 
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getbyId(@PathVariable long id) {
        return serviciosArtista.getbyId(id); 
    }

    @PostMapping("/anadir")
    public ResponseEntity<?> postArtista(@Valid @RequestBody ArtistRequest input) {
        return serviciosArtista.postArtista(input); 
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> updateArtista(@PathVariable Long id, @Valid @RequestBody ArtistRequest inputArtista) {
        return serviciosArtista.updateArtista(id, inputArtista); 
    }

    @DeleteMapping("/borrar/{id}")
    public ResponseEntity<?> deleteArtista(@PathVariable Long id) {
        return serviciosArtista.deleteArtista(id);
    }

    @GetMapping("/by-user/{username}")
    public ResponseEntity<?> getByUser(@PathVariable String username) {
    return serviciosArtista.getByUser(username);
    }



}
