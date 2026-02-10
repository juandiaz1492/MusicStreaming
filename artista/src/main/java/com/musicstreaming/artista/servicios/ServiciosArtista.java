package com.musicstreaming.artista.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.springframework.web.bind.annotation.PathVariable;


import com.musicstreaming.artista.entities.Artista;
import com.musicstreaming.artista.repository.ArtistaRepository;

@Service
public class ServiciosArtista {

    @Autowired // para utilizar metodos de artistaRepository
    ArtistaRepository artistaRepository;


    public ResponseEntity<?> findAll() {
        List<Artista> users = artistaRepository.findAll();

        if(users.isEmpty()){
            return ResponseEntity.noContent().build(); 
        }
        return ResponseEntity.ok(users); 
    }

    public ResponseEntity<?> getbyId(long id) {
        Optional<Artista> find = artistaRepository.findById(id); 
        if( find.isPresent()){
            return ResponseEntity.ok(find); 
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); 
    }


    public ResponseEntity<Artista> postArtista(Artista input) {
        Artista artista = artistaRepository.save(input);
        return ResponseEntity.status(HttpStatus.CREATED).body(artista);
    }

    
    public ResponseEntity<Artista> updateArtista(Long id, Artista inputArtista) {

        return artistaRepository.findById(id).map(artista -> {
                    artista.setNombre(inputArtista.getNombre());
                    artista.setPhone(inputArtista.getPhone());

                    Artista updated = artistaRepository.saveAndFlush(artista);
                    return ResponseEntity.ok(updated);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


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
