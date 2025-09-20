package com.eam.LevelUpCorp.persistenceLayer.entity;

import java.time.LocalDate;
import java.time.LocalTime;

public class ProgressHistory {

    private int id;
    private int userId;
    private int courseId;
    private int moduleId;
    private LocalDate accesDate;
    private LocalTime timpoDedicated;
    private String status;
    private double ModuleProgress;
    private int evaluationAttempts;

    public ProgressHistory(int id, int userId, int courseId, int moduleId, LocalDate accesDate,
                           LocalTime timpoDedicated, String status, double moduleProgress,
                           int evaluationAttempts) {
        this.id = id;
        this.userId = userId;
        this.courseId = courseId;
        this.moduleId = moduleId;
        this.accesDate = accesDate;
        this.timpoDedicated = timpoDedicated;
        this.status = status;
        ModuleProgress = moduleProgress;
        this.evaluationAttempts = evaluationAttempts;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getModuleId() {
        return moduleId;
    }

    public void setModuleId(int moduleId) {
        this.moduleId = moduleId;
    }

    public LocalDate getAccesDate() {
        return accesDate;
    }

    public void setAccesDate(LocalDate accesDate) {
        this.accesDate = accesDate;
    }

    public LocalTime getTimpoDedicated() {
        return timpoDedicated;
    }

    public void setTimpoDedicated(LocalTime timpoDedicated) {
        this.timpoDedicated = timpoDedicated;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getModuleProgress() {
        return ModuleProgress;
    }

    public void setModuleProgress(double moduleProgress) {
        ModuleProgress = moduleProgress;
    }

    public int getEvaluationAttempts() {
        return evaluationAttempts;
    }

    public void setEvaluationAttempts(int evaluationAttempts) {
        this.evaluationAttempts = evaluationAttempts;
    }
}
