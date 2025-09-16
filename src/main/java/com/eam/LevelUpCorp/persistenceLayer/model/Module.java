package com.eam.LevelUpCorp.persistenceLayer.model;

public class Module {

    private int id;
    private int cursoId;
    private String titulo;
    private String tipo; //video, texto, quiz y practica
    private int orden;

    public Module(int id, int cursoId, String titulo, String tipo, int orden) {
        this.id = id;
        this.cursoId = cursoId;
        this.titulo = titulo;
        this.tipo = tipo;
        this.orden = orden;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCursoId() {
        return cursoId;
    }

    public void setCursoId(int cursoId) {
        this.cursoId = cursoId;
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

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }
}
