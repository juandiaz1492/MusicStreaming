package com.musicstreaming.artista.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


import java.util.Set;

@Data
@Schema(name = "SongResponse", description = "Datos de salida de una canción")
public class CancionResponse {

    @Schema(example = "100", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    @Schema(example = "Tití Me Preguntó", requiredMode = Schema.RequiredMode.REQUIRED)
    private String title;

    @Schema(example = "190")
    private Integer duration;

    @Schema(example = "https://cdn.midominio.com/songs/titi-me-pregunto.mp3")
    private String url;

    @Schema(example = "10", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long albumId;
    
    @Schema(example = "[5,9]")
    private Set<Long> artistIds;

    @Schema(example = "[5,9]")
    private Set<Long> generosIds; 

}
