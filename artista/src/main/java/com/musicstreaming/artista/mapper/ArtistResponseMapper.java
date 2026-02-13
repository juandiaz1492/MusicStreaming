package com.musicstreaming.artista.mapper;

import java.util.List;


import org.mapstruct.Mapper;


import com.musicstreaming.artista.dto.ArtistResponse;
import com.musicstreaming.artista.entities.Artista;



@Mapper(componentModel = "spring")
public interface ArtistResponseMapper{
    
    ArtistResponse ArtistToArtistaResponse(Artista source); 

   
    List<ArtistResponse> listtoList(List<Artista> source);

    
}
