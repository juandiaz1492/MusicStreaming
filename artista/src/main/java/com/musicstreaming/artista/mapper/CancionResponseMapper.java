package com.musicstreaming.artista.mapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.musicstreaming.artista.dto.CancionResponse;
import com.musicstreaming.artista.entities.Cancion;

@Mapper(componentModel = "spring", imports = Collectors.class)
public interface CancionResponseMapper {

    @Mapping(target = "albumId", source = "album.id")
    @Mapping(target = "artistIds", expression = "java(mapArtistIds(song))")
    CancionResponse toResponse(Cancion song);

    List<CancionResponse> toResponseList(List<Cancion> songs);

    default Set<Long> mapArtistIds(Cancion song) {
        if (song.getArtistas() == null) return Set.of();
        return song.getArtistas().stream().map(a -> a.getId()).collect(Collectors.toSet());
    }
}