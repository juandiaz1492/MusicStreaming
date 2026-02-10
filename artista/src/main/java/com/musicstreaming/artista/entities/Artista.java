package com.musicstreaming.artista.entities;



import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;


@Data
@Entity
@Schema(name = "Artista", description = "Modelo que represena a un artista")
public class Artista {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private long id; 
    @Schema(name = "nombre", requiredMode = Schema.RequiredMode.REQUIRED, example = "bad bunny")
    private String nombre; 
    private int phone; 
    private String nombreArtistico; 
    private String dni; 


    
    






}
