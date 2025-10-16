package com.eam.LevelUpCorp.businessLayer.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO que representa una evaluación con el ID del módulo, título, tipo y puntaje máximo.")
public class EvaluationDTO {

    @Schema(description = "ID del módulo dentro del curso", example = "1")
    private Long moduleId;

    @Schema(description = "Título de la evaluación en la plataforma", example = "Evaluación final")
    private String title;

    @Schema(description = "Tipo de evaluación", example = "quiz")
    private String type;

    @Schema(description = "Puntaje máximo que se puede alcanzar en la evaluación", example = "10")
    private int maxScore;
}
