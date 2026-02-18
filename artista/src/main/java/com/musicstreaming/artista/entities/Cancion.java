package com.musicstreaming.artista.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "canciones", uniqueConstraints = {
        @UniqueConstraint(name = "uk_songs_title", columnNames = "title"),
        @UniqueConstraint(name = "uk_songs_url", columnNames = "url")
})
public class Cancion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String title;

    private Integer duration;

    @Column(unique = true)
    private String url;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "album_id", nullable = true)
    private Album album;

    //eliminar cancion no elimina artista
    @Column(nullable = false)
    @ManyToMany
    @JoinTable(name = "song_artists", joinColumns = @JoinColumn(name = "song_id"), inverseJoinColumns = @JoinColumn(name = "artist_id"))
    private Set<Artista> artistas = new HashSet<>();

    //eliminar cancion no elimina generos 
    @Column(nullable = false)
    @ManyToMany
    @JoinTable(name = "song_genres", joinColumns = @JoinColumn(name = "song_id"), inverseJoinColumns = @JoinColumn(name = "genero_id"))
    private Set<Genero> generos = new HashSet<>();

}
