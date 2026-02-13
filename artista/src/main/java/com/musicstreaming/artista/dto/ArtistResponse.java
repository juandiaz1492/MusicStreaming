package com.musicstreaming.artista.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "ArtistResponse")
public class ArtistResponse {
    
    @Schema(
            name = "id",
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "10",
            description = "ID del álbum"
    )
    private Long id; 


    @Schema(
        description = "Nombre del artista (único)",
        example = "Bad Bunny",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String name;

    @Schema(
        description = "Fecha de nacimiento del artista",
        example = "1994-03-10",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String fechaNacimiento;

    @Schema(
        description = "Nacionalidad del artista",
        example = "Puerto Rico",
        requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private String nacionalidad;

 
    @Schema(
        description = "Username del usuario propietario del artista",
        example = "juan123",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String username;
}
