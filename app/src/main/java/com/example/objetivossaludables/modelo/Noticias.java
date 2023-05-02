package com.example.objetivossaludables.modelo;

public class Noticias {
    private String titulo;
    private  int titleImage;

    public Noticias(String titulo, int titleImage) {
        this.titulo = titulo;
        this.titleImage = titleImage;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getTitleImage() {
        return titleImage;
    }

    public void setTitleImage(int titleImage) {
        this.titleImage = titleImage;
    }
}
