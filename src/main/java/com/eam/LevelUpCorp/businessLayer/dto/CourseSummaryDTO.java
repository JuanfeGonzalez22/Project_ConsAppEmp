package com.eam.LevelUpCorp.businessLayer.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO que representa un resumen de los cursos en el informe de un instructor.")
public class CourseSummaryDTO {

    @Schema(description = "Identificador único del curso", example = "5")
    private Long courseId;

    @Schema(description = "Título del curso", example = "Introducción a la programación en Java")
    private String courseTitle;

    @Schema(description = "Número total de aprendices inscritos en el curso", example = "45")
    private long totalApprentices;

    @Schema(description = "Porcentaje promedio de progreso de todos los estudiantes en el curso", example = "72.3")
    private double averageProgress;

    @Schema(description = "Puntaje promedio de los estudiantes en las evaluaciones del curso", example = "80.5")
    private double averageScores;
}
