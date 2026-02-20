package com.musicstreaming.artista.exceptions;

public class AlbumNotFoundException extends RuntimeException {

    public AlbumNotFoundException(Long id) {
        super("No existe el álbum con id: " + id);
    }
}
