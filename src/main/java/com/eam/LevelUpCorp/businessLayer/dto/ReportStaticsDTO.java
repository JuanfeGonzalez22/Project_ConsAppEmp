package com.eam.LevelUpCorp.businessLayer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Data transfer object for report statistics and analytics")
public class ReportStaticsDTO {

    @Schema(description = "Unique identifier of the report", example = "1001")
    private Long reportId;

    @Schema(description = "Total number of users in the platform", example = "1500")
    private long totalUsers;

    @Schema(description = "Total number of courses available", example = "85")
    private long totalCourses;

    @Schema(description = "Total number of course registrations", example = "4500")
    private long totalRegistrations;

    @Schema(description = "Total number of certificates issued", example = "1200")
    private long totalCertificates;

    @Schema(description = "Average progress percentage across all users", example = "65.5")
    private double averageProgress;

    @Schema(description = "Average scores achieved in evaluations", example = "78.3")
    private double averageScores;

    @Schema(description = "Distribution of users by role (role -> count)", example = "{\"STUDENT\": 1200, \"INSTRUCTOR\": 50, \"ADMIN\": 5}")
    private Map<String, Long> usersByRole;

    @Schema(description = "Timestamp when the report was generated", example = "2024-01-15T10:30:00")
    private LocalDateTime createdAt;

    @Schema(description = "Timestamp when the report was last updated", example = "2024-01-15T14:45:00")
    private LocalDateTime updatedAt;
}
