package com.eam.LevelUpCorp.businessLayer.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO utilizado para iniciar sesión en la plataforma")
public class LoginDTO {

    @Schema(description = "Correo electrónico utilizado por el usuario para iniciar sesión", example = "maria.gonzalez@company.com")
    private String email;

    @Schema(description = "Contraseña utilizada por el usuario para iniciar sesión", example = "54321")
    private String password;
}
