package com.eam.LevelUpCorp.persistenceLayer.model;

public class Evaluation {

    private int id;
    private int moduloId;
    private String titulo;
    private String tipo;
    private int puntajeMax;

    public Evaluation(int id, int moduloId, String titulo, String tipo, int puntajeMax) {
        this.id = id;
        this.moduloId = moduloId;
        this.titulo = titulo;
        this.tipo = tipo;
        this.puntajeMax = puntajeMax;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getModuloId() {
        return moduloId;
    }

    public void setModuloId(int moduloId) {
        this.moduloId = moduloId;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getPuntajeMax() {
        return puntajeMax;
    }

    public void setPuntajeMax(int puntajeMax) {
        this.puntajeMax = puntajeMax;
    }
}
