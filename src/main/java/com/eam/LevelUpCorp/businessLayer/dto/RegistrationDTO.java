package com.eam.LevelUpCorp.businessLayer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO used to register a new user in the platform")
public class RegistrationDTO {

    @Schema(description = "ID of the user to enroll", example = "12")
    private Long userId;

    @Schema(description = "ID of the course to enroll in", example = "5")
    private Long courseId;

    @Schema(description = "Status of the registration (e.g., ACTIVE, COMPLETED, DROPPED)", example = "ACTIVE")
    private String status = "ACTIVE";
}
