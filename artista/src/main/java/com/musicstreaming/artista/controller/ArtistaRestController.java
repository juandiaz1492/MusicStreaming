package com.musicstreaming.artista.controller;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;


import com.musicstreaming.artista.entities.Artista;

import com.musicstreaming.artista.servicios.ServiciosArtista;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/artista")
public class ArtistaRestController {

    private final ServiciosArtista serviciosArtista;

    public ArtistaRestController(ServiciosArtista serviciosArtista) {
        this.serviciosArtista = serviciosArtista;
    }
    
    @GetMapping("/findAll")
    public ResponseEntity<?> findAll() {
        return serviciosArtista.findAll(); 
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getbyId(@PathVariable long id) {
        return serviciosArtista.getbyId(id); 
    }
    

    @PostMapping("/añadir")
    public ResponseEntity<Artista> postArtista(@RequestBody Artista input) {
        return serviciosArtista.postArtista(input); 
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Artista> updateArtista(@PathVariable Long id, @RequestBody Artista inputArtista) {
        return serviciosArtista.updateArtista(id, inputArtista); 
    }

    @DeleteMapping("/borrar/{id}")
    public ResponseEntity<?> deleteArtista(@PathVariable Long id) {
        return serviciosArtista.deleteArtista(id);
    }




}
