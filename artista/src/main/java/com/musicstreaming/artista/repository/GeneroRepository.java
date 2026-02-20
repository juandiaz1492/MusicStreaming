package com.musicstreaming.artista.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.musicstreaming.artista.entities.Genero;

public interface GeneroRepository extends JpaRepository<Genero, Long>{
    boolean existsByNombre(String nombre);
}
