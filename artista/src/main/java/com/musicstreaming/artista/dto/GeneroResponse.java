package com.musicstreaming.artista.dto;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Schema(name = "GeneroResponse", description = "Datos de salida para un género")
public class GeneroResponse {

    @Schema(example = "3", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long idGenero;

    @Schema(example = "Reggaeton", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nombre;

    @Schema(example = "Género musical urbano originado en Puerto Rico", requiredMode = Schema.RequiredMode.REQUIRED)
    private String descripcion;

    @Schema(example = "1990", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer anioOrigen;
}
