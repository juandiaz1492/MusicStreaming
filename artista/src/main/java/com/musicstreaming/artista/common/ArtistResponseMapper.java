package com.musicstreaming.artista.common;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;


import com.musicstreaming.artista.dto.ArtistResponse;
import com.musicstreaming.artista.entities.Artista;

@Mapper(componentModel = "spring")
public interface ArtistResponseMapper {
    @Mappings({
        @Mapping(source = "nombre",target ="nombreArtista"), 
        @Mapping(source = "id",target ="idArtista")
    })
    ArtistResponse ArtistToArtistaResponse(Artista source); 

   
    List<ArtistResponse> listtoList(List<Artista> source);

    @InheritInverseConfiguration
    Artista ArtistResponseToArtista(ArtistResponse source); 

    @InheritInverseConfiguration
    List<Artista> listToListInverse(List<ArtistResponse> source); 

  
     
}
