package com.eam.LevelUpCorp.businessLayer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportStaticsDTO {



    private Long reportId;

    private long totalUsers;
    private long totalCourses;
    private long totalRegistrations;
    private long totalCertificates;
    private double averageProgress;
    private double averageScores;

    private Map<String, Long> usersByRole;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


}
