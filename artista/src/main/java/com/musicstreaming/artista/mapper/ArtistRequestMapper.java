package com.musicstreaming.artista.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.musicstreaming.artista.dto.ArtistRequest;
import com.musicstreaming.artista.entities.Artista;


@Mapper(componentModel = "spring")
public interface ArtistRequestMapper {
   
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "albums", ignore = true)
    @Mapping(target = "canciones", ignore = true)
    Artista artistaRequestToArtista(ArtistRequest artistRequest); 
}