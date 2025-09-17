package com.eam.LevelUpCorp.businessLayer.dto;

public class AnswerDTO {

    @Schema(description = "ID de la evaluación a la que responde",
           example = "12",
           required = true)
    private Long evaluationID;

    @Schema(description = "ID del usuario que responde",
            example = "45",
            required = true)
    private Long userId;

    @Schema(description = "Puntaje obtenido (si aplica para creación)",
            example = "85.0")
    private Double score;
}
