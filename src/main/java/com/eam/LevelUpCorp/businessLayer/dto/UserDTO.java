
package com.eam.LevelUpCorp.businessLayer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO that represents the basic information of a user in the platform")
public class UserDTO {

    @Schema(description = "Full name of the user", example = "John Smith")
    private String fullName;

    @Schema(description = "User's email address", example = "john.smith@company.com")
    private String email;

    @Schema(description = "User's password", example = "123456")
    private String password;

    @Schema(description = "Role of the user in the platform (ADMIN, INSTRUCTOR, USER)", example = "USER")
    private String role;

    @Schema(description = "Department the user belongs to", example = "Human Resources")
    private String department;


}
