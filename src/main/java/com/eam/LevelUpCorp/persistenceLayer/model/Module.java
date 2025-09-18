package com.eam.LevelUpCorp.persistenceLayer.model;

public class Module {

    private int id;
    private int courseId;
    private String title;
    private String type; //video, texto, quiz y practica
    private int order;

    public Module(int id, int courseId, String title, String type, int order) {
        this.id = id;
        this.courseId = courseId;
        this.title = title;
        this.type = type;
        this.order = order;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
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

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
