package com.eam.LevelUpCorp.persistenceLayer.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "progress_history") // ← Nombre exacto de tu tabla
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProgressHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id") // ← nombre exacto de columna
    private Long userId;

    @Column(name = "course_id")
    private Long courseId;

    @Column(name = "module_id")
    private Long moduleId;

    @Column(name = "registration_id")
    private Long registrationId;

    @Column(name = "access_date")
    private LocalDate accessDate = LocalDate.now();

    @Column(name = "time_dedicated")
    private LocalTime timeDedicated;

    private String status;

    @Column(name = "module_progress") // ← snake_case para BD
    private double moduleProgress;

    @Column(name = "evaluation_attempts")
    private int evaluationAttempts;
}