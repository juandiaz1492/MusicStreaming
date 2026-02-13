package com.musicstreaming.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(name = "UserRequest", description = "Datos de entrada para crear un usuario")
public class UserRequest {
    
    @Schema(
            name = "name",
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "Juan",
            description = "Nombre del usuario"
    )
    @NotBlank //obligatorio
    private String name;

    @Schema(
            name = "phone",
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "634 123 456",
            description = "Teléfono del usuario"
    )
    private String phone;

    @Schema(
            name = "password",
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "contraseña123",
            description = "Contraseña para el usuario"
    )
    private String password; 

    @Schema(name = "dni", 
            example = "12345678A", 
            description = "NIE o DNI del usuario", 
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank //obligatorio
    private String dni; 

}
