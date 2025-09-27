package com.eam.LevelUpCorp.businessLayer.dto;

import java.time.LocalTime;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Data transfer object that represents a course with description, title, estimated duration, and difficulty level.")
public class CourseDTO {

    @Schema(description = "Short description of the course")
    private String description;

    @Schema(description = "Title of the course")
    private String title;

    @Schema(description = "Estimated duration of the course in hours")
    private LocalTime estimatedDuration;

    @Schema(description = "Difficulty level of the course", example = "basic")
    private int level;
}
