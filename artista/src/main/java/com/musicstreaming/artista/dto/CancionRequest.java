package com.musicstreaming.artista.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Set;

@Data
@Schema(name = "CancionRequest", description = "Datos de entrada para crear/actualizar una canción")
public class CancionRequest {

    @NotBlank
    @Schema(example = "Tití Me Preguntó", requiredMode = Schema.RequiredMode.REQUIRED)
    private String title;

    @Schema(example = "190", description = "Duración en segundos", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Integer duration;

    @Schema(example = "https://cdn.midominio.com/songs/titi-me-pregunto.mp3", description = "URL única de la canción")
    private String url;

    @NotNull
    @Schema(example = "10", description = "ID del álbum", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long albumId;

    @Schema(example = "[5,9]", description = "IDs de artistas (opcional)")
    private Set<Long> artistIds;
}

