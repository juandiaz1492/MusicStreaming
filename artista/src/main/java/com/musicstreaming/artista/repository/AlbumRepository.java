package com.musicstreaming.artista.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.musicstreaming.artista.entities.Album;


public interface AlbumRepository extends JpaRepository<Album, Long>{
    boolean existsByNombre(String nombre);
}
