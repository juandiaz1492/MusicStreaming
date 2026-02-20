package com.musicstreaming.artista.servicios;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.musicstreaming.artista.dto.CancionRequest;
import com.musicstreaming.artista.dto.CancionResponse;
import com.musicstreaming.artista.entities.Album;
import com.musicstreaming.artista.entities.Artista;
import com.musicstreaming.artista.entities.Cancion;
import com.musicstreaming.artista.entities.Genero;
import com.musicstreaming.artista.exceptions.AlbumNotFoundException;
import com.musicstreaming.artista.exceptions.DuplicateCancionException;
import com.musicstreaming.artista.mapper.CancionRequestMapper;
import com.musicstreaming.artista.mapper.CancionResponseMapper;
import com.musicstreaming.artista.repository.AlbumRepository;
import com.musicstreaming.artista.repository.ArtistaRepository;
import com.musicstreaming.artista.repository.CancionRepository;
import com.musicstreaming.artista.repository.GeneroRepository;

import jakarta.transaction.Transactional;

@Service
public class ServiciosCancion {

    private final CancionRepository cancionRepository;
    private final AlbumRepository albumRepository;
    private final CancionRequestMapper cancionRequestMapper;
    private final ArtistaRepository artistaRepository;
    private final CancionResponseMapper cancionResponseMapper;
    private final GeneroRepository generoRepository;

    public ServiciosCancion(
            CancionRepository cancionRepository,
            AlbumRepository albumRepository,
            CancionRequestMapper cancionRequestMapper,
            CancionResponseMapper cancionResponseMapper,
            GeneroRepository generoRepository,
            ArtistaRepository artistaRepository) {
        this.cancionRepository = cancionRepository;
        this.albumRepository = albumRepository;
        this.cancionRequestMapper = cancionRequestMapper;
        this.cancionResponseMapper = cancionResponseMapper;
        this.generoRepository = generoRepository;
        this.artistaRepository = artistaRepository;
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

    public ResponseEntity<?> postCancion(CancionRequest input) {
        Album album = null;

        // Album NO TIENE PQ existir
        if (input.getAlbumId() != null) {
            album = albumRepository.findById(input.getAlbumId())
                    .orElseThrow(() -> new AlbumNotFoundException(input.getAlbumId()));
        }

        // Géneros deben existir TODOS
        Set<Long> requestedGeneroIds = new HashSet<>(input.getGenerosIds());

        List<Genero> generos = generoRepository.findAllById(requestedGeneroIds);

        Set<Long> foundGeneroIds = generos.stream()
                .map(Genero::getId)
                .collect(Collectors.toSet());

        if (!foundGeneroIds.equals(requestedGeneroIds)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existen los géneros con id(s): " + requestedGeneroIds);
        }
        // Artistas deben existir TODOS
        Set<Long> requestedArtistIds = new HashSet<>(input.getArtistIds());

        List<Artista> artistas = artistaRepository.findAllById(requestedArtistIds);

        Set<Long> foundArtistIds = artistas.stream()
                .map(Artista::getId)
                .collect(Collectors.toSet());

        if (!foundArtistIds.equals(requestedArtistIds)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existen los artistas con id(s): " + requestedArtistIds);
        }

        // comprobar que url y title son unicos
        String title = input.getTitle().trim();

        if (cancionRepository.existsByTitle(title)) {
            throw new DuplicateCancionException("Ya existe una canción con ese título");
        }

        if (input.getUrl() != null && !input.getUrl().isBlank()
                && cancionRepository.existsByUrl(input.getUrl().trim())) {
            throw new DuplicateCancionException("Ya existe una canción con esa URL");
        }

        // Crear Cancion
        Cancion c = cancionRequestMapper.cancionRequestToCancion(input);

        if (input.getAlbumId() != null) {
            c.setAlbum(album);
        }
        c.setArtistas(new HashSet<>(artistas));
        c.setGeneros(new HashSet<>(generos));

        Cancion save = cancionRepository.save(c);
        CancionResponse response = cancionResponseMapper.toResponse(save);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Transactional
    public ResponseEntity<?> updateCancion(Long id, CancionRequest inputCancion) {

        Optional<Cancion> songOpt = cancionRepository.findById(id);
        if (songOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe la canción con id " + id);
        }

        Cancion song = songOpt.get();

        // ===== Album (si viene) =====
        if (inputCancion.getAlbumId() != null) {
            Album album = albumRepository.findById(inputCancion.getAlbumId()).orElse(null);
            if (album == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No existe el álbum con id " + inputCancion.getAlbumId());
            }
            song.setAlbum(album);
        } else {
            song.setAlbum(null);
        }

        // ===== Campos simples (si vienen) =====
        if (inputCancion.getTitle() != null)
            song.setTitle(inputCancion.getTitle());

        if (inputCancion.getDuration() != null)
            song.setDuration(inputCancion.getDuration());
        else {
            song.setDuration(null);
        }

        if (inputCancion.getUrl() != null)
            song.setUrl(inputCancion.getUrl());
        else {
            song.setUrl(null);
        }

        // ===== Géneros (si vienen) =====
        if (inputCancion.getGenerosIds() != null) {

            Set<Long> requestedGeneroIds = new HashSet<>(inputCancion.getGenerosIds());

            List<Genero> generos = generoRepository.findAllById(requestedGeneroIds);

            Set<Long> foundGeneroIds = generos.stream()
                    .map(Genero::getId)
                    .collect(Collectors.toSet());

            if (!foundGeneroIds.equals(requestedGeneroIds)) {
                requestedGeneroIds.removeAll(foundGeneroIds); // ahora solo faltantes
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No existen los géneros con id(s): " + requestedGeneroIds);
            }

            // por ser realcion n-n
            song.getGeneros().clear();
            song.getGeneros().addAll(generos);
        }

        // ===== Artistas (si vienen) =====
        if (inputCancion.getArtistIds() != null) {

            Set<Long> requestedArtistIds = new HashSet<>(inputCancion.getArtistIds());

            List<Artista> artistas = artistaRepository.findAllById(requestedArtistIds);

            Set<Long> foundArtistIds = artistas.stream()
                    .map(Artista::getId)
                    .collect(Collectors.toSet());

            if (!foundArtistIds.equals(requestedArtistIds)) {
                requestedArtistIds.removeAll(foundArtistIds); // ahora solo faltantes
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No existen los artistas con id(s): " + requestedArtistIds);
            }

            // por ser relacion n-n
            song.getArtistas().clear();
            song.getArtistas().addAll(artistas);
        }

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
