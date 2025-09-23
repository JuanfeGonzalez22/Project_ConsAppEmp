package com.eam.LevelUpCorp.businessLayer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO that represents a report of an instructor and their assigned courses")
public class InstructorReportDTO {



    @Schema(description = "Unique identifier of the instructor", example = "12")
    private Long instructorId;

    @Schema(description = "Full name of the instructor", example = "Erickson jonson")
    private String instructorName;

    @Schema(description = "List of courses assigned to the instructor with their statistics")
    private List<CourseSummaryDTO> assignedCourses;


}
