package com.eam.LevelUpCorp.businessLayer.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Este DTO se usa para hacer un login en la plataforma")
public class LoginDTO {
    @Schema(description = "Correo con el que la persona entra", example = "Maria.gonzalez@empresa.com")
    private String email;

    @Schema(description = "Contraseña que la persona usa para entrar", example = "54321")
    private String password;
}