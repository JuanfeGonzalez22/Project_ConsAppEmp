package com.eam.LevelUpCorp.businessLayer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para exponer información del usuario")
public class UserRegisterResponseDTO {


    @Schema(description = "ID del usuario", example = "1")
    private Long id;

    @Schema(description = "Nombre completo del usuario", example = "Juan Pérez")
    private String name;

    @Schema(description = "Correo electrónico del usuario", example = "juan.perez@email.com")
    private String email;

    @Schema(description = "Tipo de usuario", example = "Aprendiz")
    private String role;

    @Schema(description = "Departamento del usuario", example = "Recursos Humanos")
    private String department;


}
