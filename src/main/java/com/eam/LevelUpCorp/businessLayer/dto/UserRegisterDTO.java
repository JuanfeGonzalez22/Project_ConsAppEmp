package com.eam.LevelUpCorp.businessLayer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para registrar un nuevo usuario (Aprendiz o Instructor)")
public class UserRegisterDTO {


    @Schema(description = "Nombre completo del usuario", example = "Juan Pérez")
    private String name;

    @Schema(description = "Correo electrónico único del usuario", example = "juan.perez@email.com")
    private String email;

    @Schema(description = "Contraseña del usuario, mínimo 6 caracteres", example = "password123")
    private String password;

    @Schema(description = "Tipo de usuario: 'Aprendiz' o 'Instructor'", example = "Aprendiz")
    private String role; // opcional: si decides que el usuario elija el rol en registro



}
