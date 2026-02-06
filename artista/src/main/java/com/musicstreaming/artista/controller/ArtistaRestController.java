package com.musicstreaming.artista.controller;



import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;


import com.musicstreaming.artista.entities.Artista;
import com.musicstreaming.artista.repository.ArtistaRepository;

import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/artista")
public class ArtistaRestController {

    @Autowired // para utilizar metodos de artistaRepository
    ArtistaRepository artistaRepository;


    @GetMapping("/findAll")
    public List<Artista> findAll() {
        return artistaRepository.findAll();
    }

    @GetMapping("/{id}")
    public Artista getbyId(@PathVariable long id) {
        return artistaRepository.findById(id).get();
    }
    

    @PostMapping("/añadir")
    public ResponseEntity<Artista> postArtista(@RequestBody Artista input) {
        Artista artista = artistaRepository.saveAndFlush(input);
        return ResponseEntity.ok(artista);
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Artista> updateArtista(@PathVariable Long id, @RequestBody Artista inputArtista) {

        return artistaRepository.findById(id).map(artista -> {
                    artista.setNombre(inputArtista.getNombre());
                    artista.setPhone(inputArtista.getPhone());

                    Artista updated = artistaRepository.saveAndFlush(artista);
                    return ResponseEntity.ok(updated);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/borrar/{id}")
    public ResponseEntity<?> deleteArtista(@PathVariable Long id) {

        Artista artista = artistaRepository.findById(id).orElse(null);

        if (artista != null) {
            artistaRepository.delete(artista);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }




}
