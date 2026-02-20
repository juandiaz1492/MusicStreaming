package com.musicstreaming.artista.exceptions;

public class DuplicateAlbumException extends RuntimeException {
        public DuplicateAlbumException(String message) {
            super(message);
        }
    }