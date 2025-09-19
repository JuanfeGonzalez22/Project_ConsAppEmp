package com.eam.LevelUpCorp.businessLayer.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class ProgressHistoryDTO {

    private int userId;
    private int courseId;
    private int moduleId;
    private LocalDate accesDate;
    private LocalTime timpoDedicated;
    private String status;
    private double ModuleProgress;
    private int evaluationAttempts;

}
