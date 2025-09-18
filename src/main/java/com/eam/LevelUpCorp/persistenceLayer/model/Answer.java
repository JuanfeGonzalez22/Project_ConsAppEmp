package com.eam.LevelUpCorp.persistenceLayer.model;

import java.time.LocalDate;

public class Answer {

    private int id;
    private int evaluationId;
    private int userId;
    private double score;
    private LocalDate date;

    public Answer(int id, int evaluationId, int userId, double score, LocalDate date) {
        this.id = id;
        this.evaluationId = evaluationId;
        this.userId = userId;
        this.score = score;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEvaluationId() {
        return evaluationId;
    }

    public void setEvaluationId(int evaluationId) {
        this.evaluationId = evaluationId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
