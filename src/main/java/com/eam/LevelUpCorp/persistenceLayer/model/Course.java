package com.eam.LevelUpCorp.persistenceLayer.model;

import java.time.LocalTime;

public class Course {

    private int id;
    private String description;
    private String title;
    private LocalTime estimatedDuration;
    private int level;

    public Course(int id, String description, String title, LocalTime estimatedDuration, int level) {
        this.id = id;
        this.description = description;
        this.title = title;
        this.estimatedDuration = estimatedDuration;
        this.level = level;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalTime getEstimatedDuration() {
        return estimatedDuration;
    }

    public void setEstimatedDuration(LocalTime estimatedDuration) {
        this.estimatedDuration = estimatedDuration;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
