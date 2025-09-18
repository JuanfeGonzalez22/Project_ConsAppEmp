package com.eam.LevelUpCorp.persistenceLayer.model;

import java.time.LocalDate;

public class Certificate {

    private int id;
    private int userId;
    private int courseId;
    private LocalDate emissionDate;
    private String hash;

    public Certificate(int id, int userId, int courseId, LocalDate emissionDate, String hash) {
        this.id = id;
        this.userId = userId;
        this.courseId = courseId;
        this.emissionDate = emissionDate;
        this.hash = hash;
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

    public LocalDate getEmissionDate() {
        return emissionDate;
    }

    public void setEmissionDate(LocalDate emissionDate) {
        this.emissionDate = emissionDate;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}
