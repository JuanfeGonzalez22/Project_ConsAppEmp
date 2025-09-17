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

    private String email;
    private String password;
}