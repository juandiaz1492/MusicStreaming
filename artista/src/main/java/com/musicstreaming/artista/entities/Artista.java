package com.musicstreaming.artista.entities;



import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;


@Data
@Entity
@Table(name = "artists",
    uniqueConstraints = {
    @UniqueConstraint(name = "uk_artists_name", columnNames = "name")
  }
) 
public class Artista {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id; 
    
    @Column(nullable = false, unique = true)
    private String name; 

    @Column(nullable = false) 
    private LocalDate fechaNacimiento; 

    private String nacionalidad; 

    @Column(nullable = false)
    private String username; 

    @OneToMany(mappedBy = "artistaAlbum", cascade = CascadeType.ALL,  orphanRemoval = true)
    private List<Album> albums;

    @ManyToMany(mappedBy = "artistas")
    private List<Cancion> canciones;


    
    






}
