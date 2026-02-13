package com.musicstreaming.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(name = "UserResponde", description = "Datos de entrada para devolver un usuario")
public class UserResponse {
    
    @Schema(example = "1", 
            description = "ID del usuario", 
            requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    @Schema(
            name = "nombre",
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "Juan",
            description = "Nombre del usuario"
    )
    @NotBlank
    private String name;

    @Schema(
            name = "phone",
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "634 123 456",
            description = "Teléfono del usuario"
    )
    private String phone;


    @Schema(name = "dni", 
            example = "12345678A", 
            description = "NIE o DNI del usuario", 
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    private String dni; 

}
