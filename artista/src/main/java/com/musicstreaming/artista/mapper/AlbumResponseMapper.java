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
    @Mapping(target = "fechaCreacion", expression = "java(album.getFechaCreacion() != null ? album.getFechaCreacion().toString() : null)")
    AlbumResponse toResponse(Album album);

    List<AlbumResponse> listAlbumToListAlbumResponse(List<Album> albums);
}
