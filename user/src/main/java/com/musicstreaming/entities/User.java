package com.musicstreaming.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity //se guarda en una tabla en la BBDD
@Table(name = "users") // o "app_user" para que H2 no de error pq USER era una P reservada
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; 
    private String name; 
    private String phone; 
    private String password; 
    //@Column(nullable = false, unique = true) //para que no puede ser null y sea único 
    private String dni; 

    //1 usuario -> muchos artistas
    //mapped INDICA que la clave foránea esta en user de la clase UserArtist
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserArtist> artistas = new ArrayList<>(); 
    
}
