package com.eam.LevelUpCorp.businessLayer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Data transfer object for creating an evaluation answer")
public class AnswerDTO {

    @Schema(description = "ID of the evaluation being answered",
           example = "12",
           required = true)
    private Long evaluationID;

    @Schema(description = "ID of the user submitting the answer",
            example = "45",
            required = true)
    private Long userId;

    @Schema(description = "Score obtained (if applicable when creating the answer)",
            example = "85.0")
    private Double score;
}
