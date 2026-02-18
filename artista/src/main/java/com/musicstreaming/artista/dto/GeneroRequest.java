package com.musicstreaming.artista.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Schema(name = "GeneroRequest", description = "Datos de entrada para crear/actualizar un género")
public class GeneroRequest {

    @NotBlank
    @Schema(example = "Reggaeton", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nombre;

    @Schema(example = "Género musical urbano originado en Puerto Rico", requiredMode = Schema.RequiredMode.REQUIRED)
    private String descripcion;

    @NotNull
    @Schema(example = "1990", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer anioOrigen;
}
