package com.eam.LevelUpCorp.businessLayer.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO used to create a new report in the platform")
public class ReportDTO {


    @Schema(description = "ID of the user associated with the report", example = "5")
    private Long userId;

    @Schema(description = "ID of the course associated with the report", example = "12")
    private Long courseId;

    @Schema(description = "Title of the report", example = "Monthly Course Progress")
    private String title;

    @Schema(description = "Detailed description of the report", example = "This report shows the student progress for September")
    private String description;

}
