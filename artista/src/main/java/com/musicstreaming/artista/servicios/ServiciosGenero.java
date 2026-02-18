package com.musicstreaming.artista.servicios;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.musicstreaming.artista.dto.GeneroRequest;
import com.musicstreaming.artista.dto.GeneroResponse;
import com.musicstreaming.artista.entities.Cancion;
import com.musicstreaming.artista.entities.Genero;
import com.musicstreaming.artista.mapper.GeneroRequestMapper;
import com.musicstreaming.artista.mapper.GeneroResponseMapper;
import com.musicstreaming.artista.repository.GeneroRepository;

@Service
public class ServiciosGenero {

    private final GeneroRepository generoRepository;
    private final GeneroRequestMapper generoRequestMapper;
    private final GeneroResponseMapper generoResponseMapper;

    public ServiciosGenero(GeneroRepository generoRepository,
            GeneroRequestMapper generoRequestMapper,
            GeneroResponseMapper generoResponseMapper) {
        this.generoRepository = generoRepository;
        this.generoRequestMapper = generoRequestMapper;
        this.generoResponseMapper = generoResponseMapper;
    }

    // find all
    public ResponseEntity<?> findAll() {
        List<Genero> generos = generoRepository.findAll();

        if (generos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<GeneroResponse> response = generoResponseMapper.listToListResponse(generos);

        return ResponseEntity.ok(response);
    }

    // find by {id}
    public ResponseEntity<?> getById(Long id) {
        Optional<Genero> genero = generoRepository.findById(id);

        if (genero.isPresent()) {
            GeneroResponse response = generoResponseMapper.toResponse(genero.get());
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // post añadir
    public ResponseEntity<?> postGenero(GeneroRequest input) {

        Genero genero = generoRequestMapper.toEntity(input);
        Genero save = generoRepository.save(genero);
        GeneroResponse response = generoResponseMapper.toResponse(save);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // actualizar
    public ResponseEntity<?> updateGenero(Long id, GeneroRequest input) {

        Optional<Genero> generoOpt = generoRepository.findById(id);

        if (generoOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Genero genero = generoOpt.get();

        genero.setNombre(input.getNombre());
        genero.setDescripcion(input.getDescripcion());
        genero.setAnioOrigen(input.getAnioOrigen());

        Genero actualizado = generoRepository.save(genero);

        GeneroResponse response = generoResponseMapper.toResponse(actualizado);

        return ResponseEntity.ok(response);
    }

    // borrar
    @Transactional
    public ResponseEntity<?> deleteGenero(Long id) {

        Genero g = generoRepository.findById(id).orElse(null);

        if (g == null) {
            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("Genero no encontrado con id: " + id);
        }

        // Romper relación N-N
        for (Cancion c : new HashSet<>(g.getCanciones())) {
            c.getGeneros().remove(g);
        }

        generoRepository.delete(g);

        return ResponseEntity.noContent().build();
    }

}