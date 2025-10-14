package com.eam.LevelUpCorp.businessLayer.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Data transfer object that represents a course with description, title, estimated duration, and difficulty level.")
public class CourseResponseDTO {


    @Schema(description = "ID del curso", example = "1")
    private Long id;

    @Schema(description = "Short description of the course")
    private String description;

    @Schema(description = "Title of the course")
    private String title;

    @Schema(
            description = "Estimated duration of the course in format HH:mm:ss",
            type = "string",
            example = "02:15:00"
    )
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime estimatedDuration;

    @Schema(description = "Difficulty level of the course", example = "1")
    private int level;


}
