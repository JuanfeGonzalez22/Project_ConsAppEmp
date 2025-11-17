package com.eam.LevelUpCorp.businessLayer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO que representa las estadísticas generales del sistema en todas las entidades")
public class GeneralReportDTO {

    @Schema(description = "Número total de usuarios en la plataforma", example = "162")
    private long totalUsers;

    @Schema(description = "Número total de cursos disponibles en la plataforma", example = "25")
    private long totalCourses;

    @Schema(description = "Número total de inscripciones a cursos", example = "480")
    private long totalRegistrations;

    @Schema(description = "Número total de certificados emitidos", example = "320")
    private long totalCertificates;

    @Schema(description = "Porcentaje promedio de progreso de todos los usuarios en sus cursos", example = "65.4")
    private double averageProgress;

    @Schema(description = "Puntaje promedio de todas las evaluaciones respondidas por los usuarios", example = "78.9")
    private double averageScores;

    @Schema(description = "Distribución de usuarios por rol (por ejemplo: ADMIN, INSTRUCTOR, APRENDIZ)",
            example = "{ \"ADMIN\": 2, \"INSTRUCTOR\": 10, \"APRENDIZ\": 150 }")
    private Map<String, Long> usersByRole;
}
