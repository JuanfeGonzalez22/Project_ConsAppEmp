package com.eam.LevelUpCorp.businessLayer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO that represents general system statistics across all entities")
public class GeneralReportDTO {



    @Schema(description = "Total number of users in the platform", example = "162")
    private long totalUsers;

    @Schema(description = "Total number of courses available in the platform", example = "25")
    private long totalCourses;

    @Schema(description = "Total number of course registrations", example = "480")
    private long totalRegistrations;

    @Schema(description = "Total number of certificates issued", example = "320")
    private long totalCertificates;

    @Schema(description = "Average progress percentage of all users in their courses", example = "65.4")
    private double averageProgress;

    @Schema(description = "Average score of all evaluations answered by users", example = "78.9")
    private double averageScores;

    @Schema(description = "Distribution of users by role (e.g. ADMIN, INSTRUCTOR, APPRENTICE)",
            example = "{ \"ADMIN\": 2, \"INSTRUCTOR\": 10, \"APPRENTICE\": 150 }")
    private Map<String, Long> usersByRole;




}
