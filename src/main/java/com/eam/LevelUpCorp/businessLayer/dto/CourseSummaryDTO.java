package com.eam.LevelUpCorp.businessLayer.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO that represents a summary of a courses an instructor's report")
public class CourseSummaryDTO {



    @Schema(description = "Unique identifier of the course", example = "5")
    private Long courseId;

    @Schema(description = "Title of the course", example = "Introduction to Java Programming")
    private String courseTitle;

    @Schema(description = "Total number of students enrolled in the course", example = "45")
    private long totalApprentices;

    @Schema(description = "Average progress percentage of all students in the course", example = "72.3")
    private double averageProgress;

    @Schema(description = "Average score of students in course evaluations", example = "80.5")
    private double averageScores;



}
