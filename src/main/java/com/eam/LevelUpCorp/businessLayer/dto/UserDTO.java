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

    private String nombre;
    private String email;
    private String password;
    private String rol;
    private String departamento;


}
