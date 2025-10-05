package com.eam.LevelUpCorp.businessLayer.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO for assigning an instructor to a course")
public class CourseInstructorDTO {

    @Schema(description = "ID of the course to assign", example = "5")
    private Long courseId;

    @Schema(description = "ID of the instructor to assign", example = "2")
    private Long instructorId;


}
