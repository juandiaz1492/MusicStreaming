package com.musicstreaming.artista.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.musicstreaming.artista.entities.Cancion;

public interface CancionRepository  extends JpaRepository<Cancion, Long>{

}
