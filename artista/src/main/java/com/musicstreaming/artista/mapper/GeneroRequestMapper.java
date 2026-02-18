package com.musicstreaming.artista.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.musicstreaming.artista.dto.GeneroRequest;
import com.musicstreaming.artista.entities.Genero;

@Mapper(componentModel = "spring")
public interface GeneroRequestMapper {

    // en request NO viene id, ni lista de canciones
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "canciones", ignore = true)
    Genero toEntity(GeneroRequest request);
}
