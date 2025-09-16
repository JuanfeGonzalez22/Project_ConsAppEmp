package com.eam.LevelUpCorp.persistenceLayer.model;

import java.time.LocalTime;

public class Course {

    private int id;
    private String descripcion;
    private String titulo;
    private LocalTime duracionEstimada;
    private int nivel;

    public Course(int id, String descripcion, String titulo, LocalTime duracionEstimada, int nivel) {
        this.id = id;
        this.descripcion = descripcion;
        this.titulo = titulo;
        this.duracionEstimada = duracionEstimada;
        this.nivel = nivel;
    }

    public int getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getTitulo() {
        return titulo;
    }

    public LocalTime getDuracionEstimada() {
        return duracionEstimada;
    }

    public int getNivel() {
        return nivel;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setDuracionEstimada(LocalTime duracionEstimada) {
        this.duracionEstimada = duracionEstimada;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }


}
