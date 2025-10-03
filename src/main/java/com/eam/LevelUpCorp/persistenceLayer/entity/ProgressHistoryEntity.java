package com.eam.LevelUpCorp.persistenceLayer.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;


@Entity
@Table(name = "progress_history")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProgressHistoryEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Long courseId;
    private Long moduleId;
    private LocalDate accesDate =  LocalDate.now();
    private LocalTime timpoDedicated;
    private String status;
    private double ModuleProgress;
    private int evaluationAttempts;

    }
