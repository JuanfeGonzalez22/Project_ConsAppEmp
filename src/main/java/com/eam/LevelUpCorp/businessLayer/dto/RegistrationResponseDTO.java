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
@Schema(description = "DTO para las respuestas de registros")
public class RegistrationResponseDTO {

    @Schema(description = "Identificador del registro", example = "1")
    private Long id;

    @Schema(description = "ID del usuario", example = "101")
    private Long userId;

    @Schema(description = "Nombre del usuario", example = "Juan Pérez")
    private String userName;

    @Schema(description = "Correo electrónico del usuario", example = "juan@email.com")
    private String userEmail;

    @Schema(description = "ID del curso", example = "202")
    private Long courseId;

    @Schema(description = "Título del curso", example = "Fundamentos de Spring Boot")
    private String courseTitle;

    @Schema(description = "Porcentaje de progreso actual", example = "75.5")
    private double progress;

    @Schema(description = "Fecha de inscripción", example = "2024-01-15")
    private LocalDate enrollmentDate;

    @Schema(description = "Estado de la inscripción", example = "ACTIVO")
    private String status;
}
