package com.eam.LevelUpCorp.persistenceLayer.model;

import java.time.LocalDate;

public class Answer {

    private int id;
    private int evaluacionId;
    private int usuarioId;
    private double puntuacion;
    private LocalDate fecha;

    public Answer(int id, int evaluacionId, int usuarioId, double puntuacion, LocalDate fecha) {
        this.id = id;
        this.evaluacionId = evaluacionId;
        this.usuarioId = usuarioId;
        this.puntuacion = puntuacion;
        this.fecha = fecha;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEvaluacionId() {
        return evaluacionId;
    }

    public void setEvaluacionId(int evaluacionId) {
        this.evaluacionId = evaluacionId;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public double getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(double puntuacion) {
        this.puntuacion = puntuacion;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
}
