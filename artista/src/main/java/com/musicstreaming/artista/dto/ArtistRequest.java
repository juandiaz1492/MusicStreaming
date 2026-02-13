package com.musicstreaming.artista.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(name = "ArtistRequest", description = "Datos de entrada para crear o actualizar un artista")
public class ArtistRequest {

    @NotBlank
    @Schema(
        description = "Nombre del artista (único)",
        example = "Bad Bunny",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String name;

    @NotBlank
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

    @NotBlank
    @Schema(
        description = "Username del usuario propietario del artista",
        example = "Juan",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String username;
}
