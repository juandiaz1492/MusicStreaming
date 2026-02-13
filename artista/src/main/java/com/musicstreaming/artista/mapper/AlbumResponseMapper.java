package com.musicstreaming.artista.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.musicstreaming.artista.dto.AlbumResponse;
import com.musicstreaming.artista.entities.Album;

@Mapper(componentModel = "spring")
public interface AlbumResponseMapper {

    @Mapping(target = "idAlbum", source = "id")
    @Mapping(target = "idArtista", source = "artistaAlbum.id")
    @Mapping(target = "fechaCreacion", ignore = true) // la seteas manualmente en el service
    AlbumResponse toResponse(Album album);

    List<AlbumResponse> listAlbumToListAlbumResponse(List<Album> albums);
}
