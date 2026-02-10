package com.musicstreaming.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;

@Data
@Entity
@Table(name = "user_artists")
public class UserArtist {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id; //id de la relacion 
    private Long artistaId; //id del artista de la relación 

    @Transient //no es necesario ya que para eso persistencia del otro microservicio 
    private String nombreArtista; 

    @JsonIgnore
    //muchos UserArtist -> un user, ya que un user puede tener muchos artistas
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class)
    @JoinColumn(name = "user_id", nullable = true)
    private User user; 


}

