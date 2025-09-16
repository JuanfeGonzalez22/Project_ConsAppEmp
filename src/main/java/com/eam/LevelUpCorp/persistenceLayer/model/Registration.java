package com.eam.LevelUpCorp.persistenceLayer.model;

import java.time.LocalDate;

public class Registration {

    private int id;
    private int usuarioId;
    private int cursoId;
    private double progreso;
    private LocalDate fechaInscripcion;
    private String estado;

    public Registration(int id, int usuarioId, int cursoId, double progreso, LocalDate fechaInscripcion, String estado) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.cursoId = cursoId;
        this.progreso = progreso;
        this.fechaInscripcion = fechaInscripcion;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public int getCursoId() {
        return cursoId;
    }

    public void setCursoId(int cursoId) {
        this.cursoId = cursoId;
    }

    public double getProgreso() {
        return progreso;
    }

    public void setProgreso(double progreso) {
        this.progreso = progreso;
    }

    public LocalDate getFechaInscripcion() {
        return fechaInscripcion;
    }

    public void setFechaInscripcion(LocalDate fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
