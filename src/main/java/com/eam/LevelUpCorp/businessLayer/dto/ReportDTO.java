package com.eam.LevelUpCorp.businessLayer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO representing a report with details about user, type, content, creation date, and format.")
public class ReportDTO {

    @Schema(description = "ID of the user related to the report", example = "15")
    private int userId;

    @Schema(description = "Type of the report (e.g., course, evaluation)", example = "course")
    private String type;

    @Schema(description = "Main content or details of the report", example = "Monthly performance analysis")
    private String content;

    @Schema(description = "Date when the report was created", example = "2025-09-20")
    private LocalDate creationDate;

    @Schema(description = "Format of the report (e.g., PDF, CSV)", example = "PDF")
    private String format;

}
