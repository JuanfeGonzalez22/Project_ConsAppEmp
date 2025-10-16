package com.eam.LevelUpCorp.businessLayer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para estadísticas y análisis de reportes")
public class ReportStaticsDTO {

    @Schema(description = "Identificador único del reporte", example = "1001")
    private Long reportId;

    @Schema(description = "Número total de usuarios en la plataforma", example = "1500")
    private long totalUsers;

    @Schema(description = "Número total de cursos disponibles", example = "85")
    private long totalCourses;

    @Schema(description = "Número total de inscripciones a cursos", example = "4500")
    private long totalRegistrations;

    @Schema(description = "Número total de certificados emitidos", example = "1200")
    private long totalCertificates;

    @Schema(description = "Porcentaje promedio de progreso entre todos los usuarios", example = "65.5")
    private double averageProgress;

    @Schema(description = "Promedio de puntajes obtenidos en las evaluaciones", example = "78.3")
    private double averageScores;

    @Schema(description = "Distribución de usuarios por rol (rol -> cantidad)", example = "{\"STUDENT\": 1200, \"INSTRUCTOR\": 50, \"ADMIN\": 5}")
    private Map<String, Long> usersByRole;

    @Schema(description = "Fecha y hora en que se generó el reporte", example = "2024-01-15T10:30:00")
    private LocalDateTime createdAt;

    @Schema(description = "Fecha y hora en que se actualizó el reporte por última vez", example = "2024-01-15T14:45:00")
    private LocalDateTime updatedAt;
}
