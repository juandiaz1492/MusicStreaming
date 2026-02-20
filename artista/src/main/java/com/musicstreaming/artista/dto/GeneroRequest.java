package com.musicstreaming.artista.dto;


import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Schema(name = "GeneroRequest", description = "Datos de entrada para crear/actualizar un género")
public class GeneroRequest {

    @NotBlank(message = "el nombre del género debe estar")
    @Schema(example = "Reggaeton", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nombre;

    
    @Schema(example = "Género musical urbano originado en Puerto Rico", requiredMode = Schema.RequiredMode.REQUIRED)
    private String descripcion;

    @NotNull(message = "el año del género es obligatorio")
    @Min(value = 1, message = "el año debe ser válido")
    @Schema(example = "1990", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer anioOrigen;

    @AssertTrue(message = "la descripción no puede ser vacía")
     @JsonIgnore
    public boolean isDescripcionValida() {
      return descripcion == null || !descripcion.isBlank();
    }
}
