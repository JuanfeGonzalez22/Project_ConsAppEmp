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


    private Double score;

    private String feedBack;
}
