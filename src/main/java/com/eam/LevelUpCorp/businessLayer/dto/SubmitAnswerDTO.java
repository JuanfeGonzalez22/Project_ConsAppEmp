package com.eam.LevelUpCorp.businessLayer.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para enviar una respuesta a una evaluación")
public class SubmitAnswerDTO {

    @Schema(description = "Identificador único de la evaluación a la que se enviará la respuesta", example = "12")
    private Long evaluationId;

}

