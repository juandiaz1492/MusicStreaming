package com.musicstreaming.artista.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(name = "AlbumRequest", description = "Datos de entrada para crear un álbum")
public class AlbumRequest {

    @Schema(
            name = "nombre",
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "Un Verano Sin Ti",
            description = "Nombre del álbum"
    )
    @NotBlank
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
    @NotNull
    private Double precio;

    @NotNull
    @Schema(example = "1", description = "ID del artista propietario", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long idArtista;
    
}
