package com.eam.LevelUpCorp.businessLayer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO used to register a new user in the platform")
public class RegistrationDTO {

    @Schema(description = "Full name of the user", example = "John Smith")
    private String fullName;

    @Schema(description = "Email of the user to register", example = "john.smith@company.com")
    private String email;

    @Schema(description = "Password chosen by the user", example = "123456")
    private String password;

    @Schema(description = "Department or area where the user works", example = "Human Resources")
    private String department;

    @Schema(description = "Role assigned to the user (ADMIN, INSTRUCTOR, USER)", example = "USER")
    private String role;
}
