package com.eam.LevelUpCorp.businessLayer.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Data transfer object that represents an evaluation with moduleId, title, type, and maximum score.")
public class EvaluationDTO {

    @Schema(description = "ID of the module inside the course", example = "001")
    private Long moduleId;

    @Schema(description = "Title of the evaluation on the platform", example = "Final evaluation")
    private String title;

    @Schema(description = "Type of the evaluation", example = "quiz")
    private String type;

    @Schema(description = "Maximum score that can be achieved in the evaluation", example = "10")
    private int maxScore;


}
