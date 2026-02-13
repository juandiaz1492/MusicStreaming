package com.musicstreaming.artista.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.musicstreaming.artista.dto.CancionRequest;
import com.musicstreaming.artista.dto.CancionResponse;
import com.musicstreaming.artista.entities.Album;
import com.musicstreaming.artista.entities.Cancion;
import com.musicstreaming.artista.mapper.CancionRequestMapper;
import com.musicstreaming.artista.mapper.CancionResponseMapper;
import com.musicstreaming.artista.repository.AlbumRepository;
import com.musicstreaming.artista.repository.CancionRepository;

import jakarta.transaction.Transactional;

@Service
public class ServiciosCancion {

    private final CancionRepository cancionRepository;
    private final AlbumRepository albumRepository; 
    private final CancionRequestMapper cancionRequestMapper;
    private final CancionResponseMapper cancionResponseMapper;

    public ServiciosCancion(
            CancionRepository cancionRepository,
            AlbumRepository albumRepository,
            CancionRequestMapper cancionRequestMapper,
            CancionResponseMapper cancionResponseMapper
    ) {
        this.cancionRepository = cancionRepository;
        this.albumRepository = albumRepository;
        this.cancionRequestMapper = cancionRequestMapper;
        this.cancionResponseMapper = cancionResponseMapper;
    }

    public ResponseEntity<?> findAll() {
        List<Cancion> canciones = cancionRepository.findAll();

        if (canciones.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<CancionResponse> responses = cancionResponseMapper.toResponseList(canciones);
        return ResponseEntity.ok(responses);
    }

    public ResponseEntity<?> getbyId(long id) {
        Optional<Cancion> find = cancionRepository.findById(id);

        if (find.isPresent()) {
            CancionResponse response = cancionResponseMapper.toResponse(find.get());
            System.out.println(response.getId());
            return ResponseEntity.ok(response);
        }
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Transactional
    public ResponseEntity<?> postCancion(CancionRequest input) {

        //el album tiene que existir 
        if (input.getAlbumId() == null) {
            return ResponseEntity.badRequest().body("albumId es obligatorio");
        }

        Optional<Album> albumOpt = albumRepository.findById(input.getAlbumId());
        if (albumOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El álbum no existe");
        }

        Cancion cancion = cancionRequestMapper.cancionRequestToCancion(input); 
        //le meto los albunes
        cancion.setAlbum(albumOpt.get());

        Cancion saved = cancionRepository.save(cancion);

        // 5) Respuesta
        CancionResponse response = cancionResponseMapper.toResponse(saved);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Transactional
    public ResponseEntity<?> updateCancion(Long id, CancionRequest inputCancion) {

        Optional<Cancion> songOpt = cancionRepository.findById(id);
        if (songOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Cancion song = songOpt.get();

        // Si te pasan albumId en update, validamos y cambiamos el álbum
        if (inputCancion.getAlbumId() != null) {
            Optional<Album> albumOpt = albumRepository.findById(inputCancion.getAlbumId());
            if (albumOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El álbum no existe");
            }
            song.setAlbum(albumOpt.get());
        }

        // Actualizar campos (title obligatorio en enunciado, pero aquí permito update parcial)
        if (inputCancion.getTitle() != null) song.setTitle(inputCancion.getTitle());
        if (inputCancion.getDuration() != null) song.setDuration(inputCancion.getDuration());
        if (inputCancion.getUrl() != null) song.setUrl(inputCancion.getUrl());

        Cancion updated = cancionRepository.save(song);

        CancionResponse response = cancionResponseMapper.toResponse(updated);
        return ResponseEntity.ok(response);
    }

    @Transactional
    public ResponseEntity<?> deleteCancion(Long id) {

        Cancion song = cancionRepository.findById(id).orElse(null);

        if (song != null) {
            cancionRepository.delete(song);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
