package com.musicstreaming.artista.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.musicstreaming.artista.entities.Artista; 


public interface ArtistaRepository extends JpaRepository<Artista, Long> {

}
