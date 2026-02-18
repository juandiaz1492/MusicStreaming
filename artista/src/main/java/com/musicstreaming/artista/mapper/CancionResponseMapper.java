package com.musicstreaming.artista.mapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.musicstreaming.artista.dto.CancionResponse;
import com.musicstreaming.artista.entities.Artista;
import com.musicstreaming.artista.entities.Cancion;
import com.musicstreaming.artista.entities.Genero;

@Mapper(componentModel = "spring", imports = Collectors.class)
public interface CancionResponseMapper {

    @Mapping(target = "albumId", source = "album.id")
    @Mapping(target = "artistIds", expression = "java(mapArtistIds(song))")
    @Mapping(target = "generosIds", expression = "java(mapGeneroIds(song))")
    CancionResponse toResponse(Cancion song);

    List<CancionResponse> toResponseList(List<Cancion> songs);

    default Set<Long> mapArtistIds(Cancion song) {
        return song.getArtistas()
                .stream()
                .map(Artista::getId)
                .collect(Collectors.toSet());
    }

    default Set<Long> mapGeneroIds(Cancion song) {
        return song.getGeneros()
                .stream()
                .map(Genero::getId)
                .collect(Collectors.toSet());
    }

}