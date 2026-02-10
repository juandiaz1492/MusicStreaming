package com.musicstreaming.artista.common;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.musicstreaming.artista.dto.ArtistRequest;
import com.musicstreaming.artista.entities.Artista;


@Mapper(componentModel = "spring")
public interface ArtistRequestMapper {
    @Mappings({
        @Mapping(source = "nombreArtista", target = "nombre")
    })
    Artista ArtistRequestToArtista(ArtistRequest source); 


    List<Artista> listToList(List<ArtistRequest> source); 

    @InheritInverseConfiguration
    ArtistRequest ArtistToArtistaRequest(Artista source); 

    @InheritInverseConfiguration
    List<ArtistRequest> listToListInverse(List<Artista> source); 
}
