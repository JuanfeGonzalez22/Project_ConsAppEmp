package com.eam.LevelUpCorp.businessLayer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO representing the instructor assigned to a course")
public class CourseInstructorResponseDTO {


    private Long id;

    @Schema(description = "ID of the course", example = "5")
    private Long courseId;

    @Schema(description = "Name of the course", example = "Java Programming")
    private String courseName;

    @Schema(description = "ID of the instructor", example = "2")
    private Long instructorId;

    @Schema(description = "Name of the instructor", example = "John Smith")
    private String instructorName;

    @Schema(description = "Date of assignment", example = "2025-10-03T14:30:00")
    private LocalDate assignedAt = LocalDate.now();
}
