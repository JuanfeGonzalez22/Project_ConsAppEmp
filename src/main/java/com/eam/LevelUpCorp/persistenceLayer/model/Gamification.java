package com.eam.LevelUpCorp.persistenceLayer.model;

public class Gamification {

    private int id;
    private String  nombre;
    private String criterio;
    private String icono;

    public Gamification(int id, String nombre, String criterio, String icono) {
        this.id = id;
        this.nombre = nombre;
        this.criterio = criterio;
        this.icono = icono;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCriterio() {
        return criterio;
    }

    public void setCriterio(String criterio) {
        this.criterio = criterio;
    }

    public String getIcono() {
        return icono;
    }

    public void setIcono(String icono) {
        this.icono = icono;
    }
}
