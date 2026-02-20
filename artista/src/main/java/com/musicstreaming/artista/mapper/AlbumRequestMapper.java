package com.musicstreaming.artista.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.musicstreaming.artista.dto.AlbumRequest;
import com.musicstreaming.artista.entities.Album;

@Mapper(componentModel = "spring")
public interface AlbumRequestMapper {
    
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "artistaAlbum", ignore = true)   // se setea en el service
    @Mapping(target = "canciones", ignore = true)   // se setea en el service
    Album toEntity(AlbumRequest request);
}