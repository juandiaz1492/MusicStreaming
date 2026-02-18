package com.musicstreaming.artista.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "generos")
@Data
@Schema(name = "Genero", description = "Modelo que representa un género musical")
public class Genero {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(example = "Reggaeton", requiredMode = Schema.RequiredMode.REQUIRED)
    @Column(nullable = false)
    private String nombre;

    @Schema(example = "Género musical urbano originado en Puerto Rico", requiredMode = Schema.RequiredMode.REQUIRED)
    @Column(nullable = false, length = 1000)
    private String descripcion;

    @Schema(example = "1990", requiredMode = Schema.RequiredMode.REQUIRED)
    @Column(nullable = false)
    private Integer anioOrigen;

    // 1 género -> muchas canciones
    @ManyToMany(mappedBy = "generos", fetch = FetchType.LAZY)
    private List<Cancion> canciones = new ArrayList<>();
}
