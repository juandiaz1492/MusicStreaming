package com.musicstreaming.artista.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
@Schema(name = "CancionRequest", description = "Datos de entrada para crear/actualizar una canción")
public class CancionRequest {

    @NotBlank(message = "titulo es obligatorio")
    @Schema(example = "Tití Me Preguntó", requiredMode = Schema.RequiredMode.REQUIRED)
    private String title;

    @Min(value = 1, message = "La duración debe ser mayor que 0")
    @Schema(example = "190", description = "Duración en segundos", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Integer duration;

    @Size(min = 1, message = "La url debe estar escrita")
    @Schema(example = "https://cdn.midominio.com/songs/titi-me-pregunto.mp3", description = "URL única de la canción")
    private String url;

    @Positive(message = "introduce el id del album")
    @Schema(example = "10", description = "ID del álbum", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long albumId;

    @NotNull(message =  "artistas obligatorios")
    @NotEmpty(message = "artistas obligatorios")
    @Schema(example = "[5,9]", description = "IDs de artistas")
    private Set<Long> artistIds;

    @NotEmpty(message = "genero obligatorio")
    @NotNull(message = "genero obligatorio")
    @Schema(example = "[1,3]", description = "IDs de los géneros")
    private Set<Long> generosIds; 

}

