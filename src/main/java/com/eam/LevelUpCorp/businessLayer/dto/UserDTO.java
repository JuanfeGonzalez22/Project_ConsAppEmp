
package com.eam.LevelUpCorp.businessLayer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO que representa la información básica de un usuario en la plataforma")
public class UserDTO {

    @Schema(description = "Identificador único del usuario", example = "1")
    private Long id;

    @Schema(description = "Nombre completo del usuario", example = "John Smith")
    private String fullName;

    @Schema(description = "Dirección de correo electrónico del usuario", example = "john.smith@company.com")
    private String email;

    @Schema(description = "Contraseña del usuario", example = "123456")
    private String password;

    @Schema(description = "Rol del usuario en la plataforma (ADMIN, INSTRUCTOR, USER)", example = "USER")
    private String role;

    @Schema(description = "Departamento al que pertenece el usuario", example = "Recursos Humanos")
    private String department;

}
