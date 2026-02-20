package com.musicstreaming.artista.servicios;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.musicstreaming.artista.dto.AlbumRequest;
import com.musicstreaming.artista.dto.AlbumResponse;
import com.musicstreaming.artista.dto.CancionResponse;
import com.musicstreaming.artista.entities.Album;
import com.musicstreaming.artista.entities.Artista;
import com.musicstreaming.artista.entities.Cancion;
import com.musicstreaming.artista.exceptions.DuplicateAlbumException;
import com.musicstreaming.artista.mapper.AlbumRequestMapper;
import com.musicstreaming.artista.mapper.AlbumResponseMapper;
import com.musicstreaming.artista.mapper.CancionResponseMapper;
import com.musicstreaming.artista.repository.AlbumRepository;
import com.musicstreaming.artista.repository.ArtistaRepository;

import jakarta.validation.Valid;

@Service
public class ServiciosAlbum {

    private final AlbumRepository albumRepository;
    private final AlbumRequestMapper albumRequestMapper;
    private final AlbumResponseMapper albumResponseMapper;
    private final ArtistaRepository artistaRepository;
    private final CancionResponseMapper cancionResponseMapper; 

    public ServiciosAlbum(AlbumRepository albumRepository,
            AlbumRequestMapper albumRequestMapper,
            AlbumResponseMapper albumResponseMapper,
            ArtistaRepository artistaRepository, 
            CancionResponseMapper cancionResponseMapper) {
        this.albumRepository = albumRepository;
        this.artistaRepository = artistaRepository;
        this.albumRequestMapper = albumRequestMapper;
        this.albumResponseMapper = albumResponseMapper;
        this.cancionResponseMapper = cancionResponseMapper; 
    }

    // find all
    public ResponseEntity<?> findAll() {

        List<Album> albums = albumRepository.findAll();
        List<AlbumResponse> responses = albumResponseMapper.listAlbumToListAlbumResponse(albums);

        if (responses.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(responses);
    }

     // find all
    public ResponseEntity<?> findAllAlbum(Long id) {

        Optional<Album> albumOpt = albumRepository.findById(id);

        if (albumOpt.isPresent()) {
            List<Cancion> canciones = albumOpt.get().getCanciones();
            List<CancionResponse> responses = cancionResponseMapper.toResponseList(canciones);   
            return ResponseEntity.ok(responses);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // getby id
    public ResponseEntity<?> getbyId(Long id) {

        Optional<Album> albumOpt = albumRepository.findById(id);

        if (albumOpt.isPresent()) {
            AlbumResponse response = albumResponseMapper.toResponse(albumOpt.get());
            response.setFechaCreacion(albumOpt.get().getFechaCreacion().toString());
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // post create
    public ResponseEntity<?> postAlbum(@Valid AlbumRequest input) {
        // comprobar que el artista existe
        Optional<Artista> artistaOpt = artistaRepository.findById(input.getIdArtista());

        if (artistaOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe el artista " + input.getIdArtista());
            
        }

        //comprobar que no esta repetido el nombre del albúm y tratar excepción precisa
        if (albumRepository.existsByNombre(input.getNombre())) {
            throw new DuplicateAlbumException("Ya existe un álbum con ese nombre");
        }


        Album album = albumRequestMapper.toEntity(input);

        album.setArtistaAlbum(artistaOpt.get());
        LocalDate fc = input.getFechaCreacion();

        if (fc == null) {
            album.setFechaCreacion(null);
        } else {
            album.setFechaCreacion(fc);
        }

        Album saved = albumRepository.save(album);

        AlbumResponse response = albumResponseMapper.toResponse(saved);
        response.setFechaCreacion(
                Optional.ofNullable(saved.getFechaCreacion())
                        .map(LocalDate::toString)
                        .orElse(null));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // update
    public ResponseEntity<?> updateAlbum(Long id, @Valid AlbumRequest inputAlbum) {

        Optional<Album> albumOpt = albumRepository.findById(id);

        if (albumOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe el album ");
            
        }

    
        Optional<Artista> artistaOpt = artistaRepository.findById(inputAlbum.getIdArtista());

        if (artistaOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe el artista " + inputAlbum.getIdArtista());
            
        }

        Album album = albumOpt.get();

        Album input = albumRequestMapper.toEntity(inputAlbum);


        //nombre
        album.setNombre(input.getNombre());
    
        //precio
        album.setPrecio(input.getPrecio());
    
    
        //album puede ser null
        if(input.getFechaCreacion()!= null){
            album.setFechaCreacion(input.getFechaCreacion());
        }else{
            album.setFechaCreacion(null);
        }        

        album.setArtistaAlbum(artistaOpt.get());

        Album actualizado = albumRepository.save(album);

        AlbumResponse response = albumResponseMapper.toResponse(actualizado);

        return ResponseEntity.ok(response);
    }

    // delete
    public ResponseEntity<?> deleteAlbum(Long id) {

        Album album = albumRepository.findById(id).orElse(null);

        if (album != null) {
            albumRepository.delete(album);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
