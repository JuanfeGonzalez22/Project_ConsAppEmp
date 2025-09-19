package com.eam.LevelUpCorp.persistenceLayer.model;

import java.time.LocalDate;

public class Report {

    private int id;
    private int userId;
    private String type;
    private String description;
    private LocalDate creationDate;
    private String format;
    private String content;

    public Report(int id, int userId, String type, String description, LocalDate creationDate, String format, String content) {
        this.id = id;
        this.userId = userId;
        this.type = type;
        this.description = description;
        this.creationDate = creationDate;
        this.format = format;
        this.content = content;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
