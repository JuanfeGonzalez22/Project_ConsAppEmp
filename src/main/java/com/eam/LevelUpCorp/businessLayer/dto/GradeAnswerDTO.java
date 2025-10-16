package com.eam.LevelUpCorp.businessLayer.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para calificar una respuesta de evaluación")
public class GradeAnswerDTO {

    @Schema(description = "Puntaje numérico asignado a la respuesta", example = "8.5")
    private Double score;

    @Schema(description = "Comentarios o retroalimentación para la respuesta evaluada", example = "¡Excelente trabajo! Bien estructurado y completo.")
    private String feedback;
}
