package com.eam.LevelUpCorp.businessLayer.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "This DTO is used to perform login in the platform")
public class LoginDTO {

    @Schema(description = "Email used by the user to log in", example = "maria.gonzalez@company.com")
    private String email;

    @Schema(description = "Password used by the user to log in", example = "54321")
    private String password;
}