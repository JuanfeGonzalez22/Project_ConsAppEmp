package com.eam.LevelUpCorp.persistenceLayer.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Entity
@Table(name = "report_stats")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportStaticsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long reportId;

    private long totalUsers;
    private long totalCourses;
    private long totalRegistrations;
    private long totalCertificates;
    private double averageProgress;
    private double averageScores;

    @ElementCollection
    @CollectionTable(name = "report_stats_users_by_role", joinColumns = @JoinColumn(name = "report_stats_id"))
    @MapKeyColumn(name = "role")
    @Column(name = "count")
    private Map<String, Long> usersByRole;

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt =  LocalDateTime.now();
}
