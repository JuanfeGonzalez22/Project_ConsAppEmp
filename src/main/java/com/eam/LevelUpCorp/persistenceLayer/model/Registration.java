package com.eam.LevelUpCorp.persistenceLayer.model;

import java.time.LocalDate;

public class Registration {

    private int id;
    private int userId;
    private int courseId;
    private double progress;
    private LocalDate enrollmentDate;
    private String status;

    public Registration(int id, int userId, int courseId, double progress, LocalDate enrollmentDate, String status) {
        this.id = id;
        this.userId = userId;
        this.courseId = courseId;
        this.progress = progress;
        this.enrollmentDate = enrollmentDate;
        this.status = status;
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

    public double getProgress() {
        return progress;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }

    public LocalDate getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(LocalDate enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
