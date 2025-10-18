package com.eam.LevelUpCorp.businessLayer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalTime;

@Schema(description = "DTO que representa el historial de progreso de un usuario en un curso o módulo")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProgressHistoryDTO {

    @Schema(description = "ID del usuario", example = "101")
    private Long userId;

    @Schema(description = "ID del curso", example = "202")
    private Long courseId;

    @Schema(description = "ID del módulo", example = "303")
    private Long moduleId;

    @Schema(description = "ID del registro de inscripción", example = "404")
    private Long registrationId;

    @Schema(description = "Tiempo dedicado por el usuario durante este acceso", example = "01:30")
    private LocalTime timeDedicated;

    @Schema(description = "Estado actual del módulo para el usuario (por ejemplo: en progreso, completado)", example = "completado")
    private String status;

    @Schema(description = "Porcentaje de progreso en el módulo", example = "75.5")
    private double moduleProgress;

    @Schema(description = "Número de intentos de evaluación realizados por el usuario", example = "2")
    private int evaluationAttempts;
}
