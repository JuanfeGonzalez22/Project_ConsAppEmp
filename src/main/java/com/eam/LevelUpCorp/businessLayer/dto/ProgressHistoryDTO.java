package com.eam.LevelUpCorp.businessLayer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalTime;

@Schema(description = "DTO representing the progress history of a user in a course/module.")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProgressHistoryDTO {

    @Schema(description = "ID of the user", example = "101")
    private int userId;

    @Schema(description = "ID of the course", example = "202")
    private int courseId;

    @Schema(description = "ID of the module", example = "303")
    private int moduleId;


    @Schema(description = "Time dedicated by the user during this access", example = "01:30")
    private LocalTime timeDedicated;

    @Schema(description = "Current status of the module for the user (e.g., in progress, completed)", example = "completed")
    private String status;

    @Schema(description = "Percentage of progress in the module", example = "75.5")
    private double ModuleProgress;

    @Schema(description = "Number of evaluation attempts made by the user", example = "2")
    private int evaluationAttempts;

}
