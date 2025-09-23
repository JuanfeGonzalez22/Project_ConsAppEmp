package com.eam.LevelUpCorp.businessLayer.dto;

public class Gamification {

//Si es fijo no es entidad, si es configurable si .
    private int id;
    private String  name;
    private String criterion;
    private String icono;

    public Gamification(int id, String name, String criterion, String icono) {
        this.id = id;
        this.name = name;
        this.criterion = criterion;
        this.icono = icono;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCriterion() {
        return criterion;
    }

    public void setCriterion(String criterion) {
        this.criterion = criterion;
    }

    public String getIcono() {
        return icono;
    }

    public void setIcono(String icono) {
        this.icono = icono;
    }
}
