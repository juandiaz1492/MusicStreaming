package com.musicstreaming.artista.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.springframework.web.bind.annotation.PathVariable;

import com.musicstreaming.artista.common.ArtistRequestMapper;
import com.musicstreaming.artista.common.ArtistResponseMapper;
import com.musicstreaming.artista.dto.ArtistRequest;
import com.musicstreaming.artista.dto.ArtistResponse;
import com.musicstreaming.artista.entities.Artista;
import com.musicstreaming.artista.repository.ArtistaRepository;

@Service
public class ServiciosArtista {

    @Autowired // para utilizar metodos de artistaRepository
    ArtistaRepository artistaRepository;

    @Autowired
    ArtistRequestMapper artistRequestMapper; 
    
    @Autowired
    ArtistResponseMapper artistResponseMapper; 

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
        Artista artista = artistRequestMapper.ArtistRequestToArtista(input); 
        Artista save = artistaRepository.save(artista);
        return ResponseEntity.status(HttpStatus.CREATED).body(save);
    }

    
    public ResponseEntity<?> updateArtista(Long id, ArtistRequest inputArtista) {

        Optional<Artista> artistaOpt = artistaRepository.findById(id);

        if (artistaOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Artista artista = artistaOpt.get();

        
        Artista input = artistRequestMapper.ArtistRequestToArtista(inputArtista);

        // Actualizar campos
        artista.setNombre(input.getNombre());
        artista.setPhone(input.getPhone());
        artista.setNombreArtistico(input.getNombreArtistico());
        artista.setDni(input.getDni());

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





}
