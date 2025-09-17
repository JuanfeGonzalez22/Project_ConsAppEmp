package com.eam.LevelUpCorp.businessLayer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO usado para registrar un nuevo usuario en la plataforma")
public class RegistrationDTO {

    @Schema(description = "Nombre completo del usuario", example = "Juan Pérez")
    private String nombre;

    @Schema(description = "Correo del usuario para registrarse", example = "juan.perez@empresa.com")
    private String email;

    @Schema(description = "Contraseña elegida por el usuario", example = "123456")
    private String password;

    @Schema(description = "Departamento o área donde trabaja el usuario", example = "Recursos Humanos")
    private String departamento;

    @Schema(description = "Rol asignado (ADMIN, INSTRUCTOR, USER)", example = "USER")
    private String rol;
}
