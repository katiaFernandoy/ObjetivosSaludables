package com.example.objetivossaludables.modelo;

import java.util.Date;

public class InformacionPersonal extends Usuario{

     private  double peso;
     private Date fechaNacimiento;
     private String genero;
     private int altura;

    public InformacionPersonal(String email, String nombre, double peso, int altura, String genero, Date fechaNacimiento) {
        super(email,nombre);
        this.peso = peso;
        this.fechaNacimiento = fechaNacimiento;
        this.genero = genero;
        this.altura = altura;
    }

    public InformacionPersonal(String email,double peso, Date fechaNacimiento, String genero, int altura) {
        super(email);
        this.peso = peso;
        this.fechaNacimiento = fechaNacimiento;
        this.genero = genero;
        this.altura = altura;
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

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }
}
