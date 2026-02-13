package com.musicstreaming.artista.servicios;


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
import com.musicstreaming.artista.entities.Artista;
import com.musicstreaming.artista.mapper.ArtistRequestMapper;
import com.musicstreaming.artista.mapper.ArtistResponseMapper;
import com.musicstreaming.artista.repository.ArtistaRepository;



@Service
public class ServiciosArtista {

    @Autowired // para utilizar metodos de artistaRepository
    ArtistaRepository artistaRepository;

    @Autowired
    ArtistRequestMapper artistRequestMapper; 
    
    @Autowired
    ArtistResponseMapper artistResponseMapper; 

    private final RestClient restClient;

    public ServiciosArtista(RestClient restClient) {
        this.restClient = restClient;
    }
    
    @Value("${user.service.url}")
    private String userServiceUrl;


    public ResponseEntity<?> findAll() {
        List<Artista> artistas = artistaRepository.findAll();

        List<ArtistResponse> artistaseResponses = artistResponseMapper.listtoList(artistas); 

        if(artistaseResponses.isEmpty()){
            return ResponseEntity.noContent().build(); 
        }
        return ResponseEntity.ok(artistaseResponses); 
    }


    public ResponseEntity<?> getbyId(long id) {
        Optional<Artista> find = artistaRepository.findById(id); 
         
        if( find.isPresent()){
            ArtistResponse response = artistResponseMapper.ArtistToArtistaResponse(find.get()); 
            return ResponseEntity.ok(response); 
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); 
    }


    public ResponseEntity<Artista> postArtista(ArtistRequest input) {
        Artista artista = artistRequestMapper.artistaRequestToArtista(input); 
        String nombreUser = artista.getUsername(); 
        if(comprobarSiExisteUser(nombreUser)){
             Artista save = artistaRepository.save(artista);
            return ResponseEntity.status(HttpStatus.CREATED).body(save);
        } 
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();  
        }
        
    }

    @SuppressWarnings("null")
    private boolean comprobarSiExisteUser(String nombreUser) {

        // Llamada al microservicio user
        JsonNode block = restClient.get()
                .uri(userServiceUrl + "/nombre/{nombreUser}", nombreUser)
                .retrieve()
                .body(JsonNode.class);

        // Comprobamos que el nombre coincida
        if(nombreUser.equals(block.get("name").asText())){
            return true; 
        }
        return false;
    }


    

    public ResponseEntity<?> updateArtista(Long id, ArtistRequest inputArtista) {

        Optional<Artista> artistaOpt = artistaRepository.findById(id);

        if (artistaOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Artista artista = artistaOpt.get();

        
        Artista input = artistRequestMapper.artistaRequestToArtista(inputArtista);

        // Actualizar campos
        artista.setName(input.getName());
        artista.setNacionalidad(input.getNacionalidad());
        artista.setFechaNacimiento(input.getFechaNacimiento());
        artista.setAlbums(input.getAlbums());
        //artista.setUsername(userServiceUrl); no se si puedo hacerlo 

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
        if (artistas.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(artistResponseMapper.listtoList(artistas));
    }






}
