package com.musicstreaming.artista.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Data;

@Data
@Schema(name = "AlbumResponse", description = "Datos de salida para un álbum")
public class AlbumResponse {

    @Schema(
            name = "idAlbum",
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "10",
            description = "ID del álbum"
    )
    private Long idAlbum;

    @Schema(
            name = "nombre",
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "Un Verano Sin Ti",
            description = "Nombre del álbum"
    )
    
    private String nombre;

    @Schema(
            name = "fechaCreacion",
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "2025-02-12",
            description = "Fecha de creación/publicación del álbum (yyyy-MM-dd)"
    )
    private String fechaCreacion;

    @Schema(
            name = "precio",
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "9.99",
            description = "Precio del álbum"
    )
   
    private Double precio;

    
    @Schema(example = "1", description = "ID del artista propietario", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long idArtista;
}
