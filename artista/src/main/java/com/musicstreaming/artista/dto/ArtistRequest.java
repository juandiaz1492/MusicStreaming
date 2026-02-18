package com.musicstreaming.artista.dto;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(name = "ArtistRequest", description = "Datos de entrada para crear o actualizar un artista")
public class ArtistRequest {

    @NotBlank(message = "El nombre es obligatorio")
    @Schema(
        description = "Nombre del artista (único)",
        example = "Bad Bunny",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String name;

    @NotNull(message = "La fecha es obligatoria")
    @Schema(
        description = "Fecha de nacimiento del artista",
        example = "1994-03-10",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private LocalDate fechaNacimiento;

    @Schema(
        description = "Nacionalidad del artista",
        example = "Puerto Rico",
        requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    @Size(min = 1, message = "La nacionalidad no puede estar vacía")
    private String nacionalidad;

    @NotBlank(message = "El nombre del usuario es obligatorio")
    @Schema(
        description = "Username del usuario propietario del artista",
        example = "Juan",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String username;
}
