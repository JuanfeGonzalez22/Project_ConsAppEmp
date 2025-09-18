package com.eam.LevelUpCorp.persistenceLayer.model;

public class Evaluation {

    private int id;
    private int moduleId;
    private String title;
    private String type;
    private int maxCore;

    public Evaluation(int id, int moduleId, String title, String type, int maxCore) {
        this.id = id;
        this.moduleId = moduleId;
        this.title = title;
        this.type = type;
        this.maxCore = maxCore;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getModuleId() {
        return moduleId;
    }

    public void setModuleId(int moduleId) {
        this.moduleId = moduleId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getMaxCore() {
        return maxCore;
    }

    public void setMaxCore(int maxCore) {
        this.maxCore = maxCore;
    }
}
