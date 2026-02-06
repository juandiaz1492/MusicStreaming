package com.musicstreaming.artista.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Album {
    @Id
    private long id; 
    private String nombre; 



}
