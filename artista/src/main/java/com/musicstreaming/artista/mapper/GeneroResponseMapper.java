package com.musicstreaming.artista.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.musicstreaming.artista.dto.GeneroResponse;
import com.musicstreaming.artista.entities.Genero;

@Mapper(componentModel = "spring")
public interface GeneroResponseMapper {

    @Mapping(target = "idGenero", source = "id")
    GeneroResponse toResponse(Genero genero);

    List<GeneroResponse> listToListResponse(List<Genero> generos);
}

