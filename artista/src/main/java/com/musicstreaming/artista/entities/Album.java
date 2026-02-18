package com.musicstreaming.artista.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

@Data
@Entity
@Table(name = "album",
    uniqueConstraints = {
    @UniqueConstraint(name = "uk_albums_nombre", columnNames = "nombre")
  }
) 

public class Album {
    
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private long id; 

    @Column(nullable = false)
    private String nombre; 

    private LocalDate fechaCreacion; 
    
    @Column(nullable = false)
    private double precio; 

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "artista_id", nullable = false)
    private Artista artistaAlbum; 

    @OneToMany(mappedBy = "album", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cancion> canciones = new ArrayList<>();

}
