package com.example.objetivossaludables.modelo;

public class Noticias {
    private String titulo;
    private int titleImage;
    private String link_btn;

    public Noticias(String titulo, int titleImage, String link_btn) {
        this.titulo = titulo;
        this.titleImage = titleImage;
        this.link_btn = link_btn;
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

    public String getLink_btn() {
        return link_btn;
    }

    public void setLink_btn(String link_btn) {
        this.link_btn = link_btn;
    }
}


