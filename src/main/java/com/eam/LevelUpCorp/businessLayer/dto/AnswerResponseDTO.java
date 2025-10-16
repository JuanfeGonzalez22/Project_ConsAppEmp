package com.eam.LevelUpCorp.businessLayer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Objeto de transferencia de datos para representar la respuesta de una evaluación.")
public class AnswerResponseDTO {

    @Schema(description = "Identificador único de la respuesta", example = "1")
    private Long id;

    @Schema(description = "Fecha y hora en que se envió la respuesta", example = "2024-01-15T14:30:00")
    private LocalDateTime date;

    @Schema(description = "Identificador de la evaluación que se está respondiendo", example = "12", required = true)
    private Long evaluationId;

    @Schema(description = "Identificador del usuario que envía la respuesta", example = "45", required = true)
    private Long userId;

    @Schema(description = "Puntaje obtenido en la evaluación", example = "85.0")
    private Double score;
}
