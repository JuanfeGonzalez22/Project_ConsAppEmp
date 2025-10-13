package com.eam.LevelUpCorp.businessLayer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO for the registrations responses")
public class RegistrationResponseDTO {


    @Schema(description = "ID of the registration", example = "1")
    private Long id;

    @Schema(description = "ID of the user", example = "101")
    private Long userId;

    @Schema(description = "Name of the user", example = "Juan Pérez")
    private String userName;

    @Schema(description = "Email of the user", example = "juan@email.com")
    private String userEmail;

    @Schema(description = "ID of the course", example = "202")
    private Long courseId;

    @Schema(description = "Title of the course", example = "Spring Boot Fundamentals")
    private String courseTitle;

    @Schema(description = "Current progress percentage", example = "75.5")
    private double progress;

    @Schema(description = "Enrollment date", example = "2024-01-15")
    private LocalDate enrollmentDate;

    @Schema(description = "Status of registration", example = "ACTIVE")
    private String status;

}
