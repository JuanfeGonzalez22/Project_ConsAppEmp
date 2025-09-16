package com.eam.LevelUpCorp.persistenceLayer.model;

import java.time.LocalDate;

public class Certificate {

    private int id;
    private int usuarioId;
    private int cursoId;
    private LocalDate fechaEmision;
    private String hash;

    public Certificate(int id, int usuarioId, int cursoId, LocalDate fechaEmision, String hash) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.cursoId = cursoId;
        this.fechaEmision = fechaEmision;
        this.hash = hash;
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

    public LocalDate getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(LocalDate fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}
