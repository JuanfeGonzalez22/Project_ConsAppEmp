package com.eam.LevelUpCorp.businessLayer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Data transfer object for creating an evaluation answer")
public class AnswerResponseDTO {


    @Schema(description = "Unique identifier of the answer", example = "1")
    private Long id;

    @Schema(description = "URL to the answer file if submitted as file", example = "https://storage.com/answers/answer-123.pdf")
    private String answerFileUrl;

    @Schema(description = "Date and time when the answer was submitted", example = "2024-01-15T14:30:00")
    private LocalDateTime date;

    @Schema(description = "ID of the evaluation being answered", example = "12", required = true)
    private Long evaluationId;

    @Schema(description = "ID of the user submitting the answer", example = "45", required = true)
    private Long userId;

    @Schema(description = "Score obtained in the evaluation", example = "85.0")
    private Double score;
}
