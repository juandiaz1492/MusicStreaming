package com.musicstreaming.artista.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.musicstreaming.artista.dto.CancionRequest;
import com.musicstreaming.artista.entities.Cancion;

@Mapper(componentModel = "spring")
public interface CancionRequestMapper {

    // los ignoro pq los compruebo antes en el servicio
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "album", ignore = true)
    @Mapping(target = "generos", ignore = true)
    @Mapping(target = "artistas", ignore = true)
    Cancion cancionRequestToCancion(CancionRequest request);
    
} 
