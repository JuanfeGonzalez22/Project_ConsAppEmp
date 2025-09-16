package com.eam.LevelUpCorp.persistenceLayer.model;

public class User {

    private int id;
    private String nombre;
    private String email;
    private String password;
    private String rol;
    private String departamento;

    public User(int id, String nombre, String email, String rol, String departamento, String password) {
        this.id = id;
        this.password = password;
        this.nombre = nombre;
        this.email = email;
        this.rol = rol;
        this.departamento = departamento;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }


}
