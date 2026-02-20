package com.musicstreaming.artista.servicios;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestClient;

import com.fasterxml.jackson.databind.JsonNode;
import com.musicstreaming.artista.dto.ArtistRequest;
import com.musicstreaming.artista.dto.ArtistResponse;
import com.musicstreaming.artista.dto.CancionResponse;

import com.musicstreaming.artista.entities.Artista;
import com.musicstreaming.artista.entities.Cancion;
import com.musicstreaming.artista.mapper.ArtistRequestMapper;
import com.musicstreaming.artista.mapper.ArtistResponseMapper;
import com.musicstreaming.artista.mapper.CancionResponseMapper;
import com.musicstreaming.artista.repository.ArtistaRepository;

import jakarta.validation.Valid;

@Service

public class ServiciosArtista {

    @Autowired // para utilizar metodos de artistaRepository
    ArtistaRepository artistaRepository;

    @Autowired
    KeycloakTokenService KeycloakTokenService; 
    
    @Autowired
    ArtistRequestMapper artistRequestMapper;

    @Autowired
    ArtistResponseMapper artistResponseMapper;

    @Autowired
    CancionResponseMapper cancionResponseMapper;  

    private final RestClient restClient;
    

    public ServiciosArtista(RestClient restClient) {
        this.restClient = restClient;
    }

    @Value("${user.service.url}")
    private String userServiceUrl;

    public ResponseEntity<?> findAll() {
        List<Artista> artistas = artistaRepository.findAll();

        List<ArtistResponse> artistaseResponses = artistResponseMapper.listtoList(artistas);

        if (artistaseResponses.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(artistaseResponses);
    }

    public ResponseEntity<?> findAllArtista(Long id) {
        Optional<Artista> artistaOpt = artistaRepository.findById(id);

        if (artistaOpt.isPresent()) {
            List<Cancion> canciones = artistaOpt.get().getCanciones();
            List<CancionResponse> responses = cancionResponseMapper.toResponseList(canciones);   
            return ResponseEntity.ok(responses);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    public ResponseEntity<?> getbyId(long id) {
        Optional<Artista> find = artistaRepository.findById(id);

        if (find.isPresent()) {
            ArtistResponse response = artistResponseMapper.ArtistToArtistaResponse(find.get());
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    public ResponseEntity<ArtistResponse> postArtista(@Valid ArtistRequest input) {
        Artista artista = artistRequestMapper.artistaRequestToArtista(input);
        
        LocalDate fc = input.getFechaNacimiento();

        if (fc == null) {
            artista.setFechaNacimiento(null);;
        } else {
            artista.setFechaNacimiento(fc);
        }
        
        String nombreUser = artista.getUsername();
        if (comprobarSiExisteUser(nombreUser)) {
            Artista save = artistaRepository.save(artista);
            ArtistResponse response = artistResponseMapper.ArtistToArtistaResponse(save); 
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }

    private boolean comprobarSiExisteUser(String nombreUser) {
        try {
        String token = KeycloakTokenService.getToken(); 

        JsonNode body = restClient.get()
                .uri(userServiceUrl + "/nombre/{nombreUser}", nombreUser) // porque userServiceUrl ya es .../user
                .header(org.springframework.http.HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .body(JsonNode.class);

        return body != null
                && body.hasNonNull("name")
                && nombreUser.equals(body.get("name").asText());

    } catch (org.springframework.web.client.HttpClientErrorException.NotFound e) {
        return false;
    } catch (org.springframework.web.client.RestClientResponseException e) {
        return false;
    }
    }

    public ResponseEntity<?> updateArtista(Long id, ArtistRequest inputArtista) {

        Optional<Artista> artistaOpt = artistaRepository.findById(id);

        if (artistaOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe ese artista");
        }
        

        Artista artista = artistaOpt.get();

        Artista input = artistRequestMapper.artistaRequestToArtista(inputArtista);

        // Actualizar campos
        artista.setName(input.getName());
        artista.setNacionalidad(input.getNacionalidad());
        artista.setFechaNacimiento(input.getFechaNacimiento());

        if (comprobarSiExisteUser(input.getUsername())) {
            artista.setUsername(input.getUsername());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe el usuario con nombre" + input.getUsername());
        }

        // Guardar cambios
        Artista actualizado = artistaRepository.save(artista);
        ArtistResponse save = artistResponseMapper.ArtistToArtistaResponse(actualizado);
        
        return ResponseEntity.ok(save);
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

    public ResponseEntity<?> getByUser(String username) {
        List<Artista> artistas = artistaRepository.findByUsername(username);
        if (artistas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(artistResponseMapper.listtoList(artistas));
    }

}
