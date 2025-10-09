package com.eam.LevelUpCorp.businessLayer.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Data transfer object for creating an evaluation answer")
public class GradeAnswerDTO {


    @Schema(description = "Numerical score assigned to the answer", example = "8.5")
    private Double score;

    @Schema(description = "Feedback comments for the evaluated answer", example = "Excellent work! Well structured and comprehensive.")
    private String feedback;

}
