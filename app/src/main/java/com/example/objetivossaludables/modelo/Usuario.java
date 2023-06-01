package com.example.objetivossaludables.modelo;

public class Usuario {

    private int id;
    private String userID;//email
    private String nombre;
    private String apellido;

    public Usuario(String userID, String nombre, String apellido) {
        this.userID = userID;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public Usuario(String userID, String nombre) {
        this.userID = userID;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
}
