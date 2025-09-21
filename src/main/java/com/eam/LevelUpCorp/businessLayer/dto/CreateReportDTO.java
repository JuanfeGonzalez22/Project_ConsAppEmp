package com.eam.LevelUpCorp.businessLayer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO used to create a report with details such as user, type, description, date, format, and content")
public class CreateReportDTO {

    @Schema(description = "ID of the user who is creating the report", example = "101")
    private int userId;

    @Schema(description = "Type of the report", example = "Evaluation")
    private String type;

    @Schema(description = "Short description of the report", example = "Monthly performance evaluation of sales team")
    private String description;

    @Schema(description = "Date when the report is created", example = "2025-09-20")
    private LocalDate creationDate;

    @Schema(description = "Format of the report", example = "PDF")
    private String format;

    @Schema(description = "The main content of the report", example = "This is the full report text or structured data")
    private String content;
}
