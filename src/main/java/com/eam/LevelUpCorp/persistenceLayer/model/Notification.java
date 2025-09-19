package com.eam.LevelUpCorp.persistenceLayer.model;

import java.time.LocalDate;

public class Notification {

    private int id;
    private int userId;
    private String type;
    private String menssage;
    private LocalDate sentDate;
    private String status;

    public Notification(int id, int userId, String type, String menssage, LocalDate sentDate, String status) {
        this.id = id;
        this.userId = userId;
        this.type = type;
        this.menssage = menssage;
        this.sentDate = sentDate;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMenssage() {
        return menssage;
    }

    public void setMenssage(String menssage) {
        this.menssage = menssage;
    }

    public LocalDate getSentDate() {
        return sentDate;
    }

    public void setSentDate(LocalDate sentDate) {
        this.sentDate = sentDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
