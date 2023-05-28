package com.example.objetivossaludables.modelo;

import java.util.Date;

public class InformacionPersonal {

     private String nombre;
     private  double peso;
     private Date fechaNacimiento;
     private String genero;
     private double altura;

    public InformacionPersonal(String nombre, double peso, Date fechaNacimiento, String genero, double altura) {
        this.nombre = nombre;
        this.peso = peso;
        this.fechaNacimiento = fechaNacimiento;
        this.genero = genero;
        this.altura = altura;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public double getAltura() {
        return altura;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }
}
