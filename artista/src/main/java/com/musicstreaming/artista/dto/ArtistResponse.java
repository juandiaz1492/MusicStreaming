package com.musicstreaming.artista.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "ArtistResponse")
public class ArtistResponse {
     
    private long idArtista; 
    private String nombreArtista; 
    
    private int phone; 
    private String nombreArtistico; 
    private String dni; 
}
