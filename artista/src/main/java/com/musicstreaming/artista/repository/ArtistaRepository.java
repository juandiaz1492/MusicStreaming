package com.musicstreaming.artista.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.musicstreaming.artista.entities.Artista; 


public interface ArtistaRepository extends JpaRepository<Artista, Long> {

    //para buscar artistas con un username
    List<Artista> findByUsername(String username);


}
