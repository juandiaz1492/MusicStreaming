package com.musicstreaming.artista.entities;

import java.sql.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import lombok.Data;

@Entity
@Data
@Schema(name = "Album", description = "Modelo que represena a un álbum")
public class Album {
    
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private long id; 

    @Column(nullable = false)
    private String nombre; 

    private Date fechaCreacion; 
    
    @Column(nullable = false)
    private double precio; 

    @ManyToOne
    @JoinColumn(name = "artista_id", nullable = false)
    private Artista artistaAlbum; 

}
