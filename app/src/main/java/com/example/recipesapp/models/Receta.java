package com.example.recipesapp.models;

public class Receta implements java.io.Serializable {
    private int id;
    private String titulo;
    private String descripcion;
    private String imagen;
    private String ingredientes;
    private String pasos;
    private int imagenResId;
    private String tipoReceta;

    public Receta(String titulo, String descripcion, String imagen, String ingredientes, String pasos, int imagenResId) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.ingredientes = ingredientes;
        this.pasos = pasos;
        this.imagenResId = imagenResId;
    }

    public Receta(int id, String titulo, String imagen, String descripcion, String ingredientes, String pasos, int imagenResId, String tipoReceta) {
        this.id = id;
        this.titulo = titulo;
        this.imagen = imagen;
        this.descripcion = descripcion;
        this.ingredientes = ingredientes;
        this.pasos = pasos;
        this.imagenResId = imagenResId;
        this.tipoReceta = tipoReceta;
    }

    public Receta(String titulo, String descripcion, int imagenResId) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.imagenResId = imagenResId;
    }

    public Receta(int id, String titulo, String descripcion, String imagen, String ingredientes, String pasos, String string) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.ingredientes = ingredientes;
        this.pasos = pasos;
    }

    public Receta(int id, String titulo, String imagen, String ingredientes) {
        this.id = id;
        this.titulo = titulo;
        this.imagen = imagen;
        this.ingredientes = ingredientes;
    }

    // Getters y setters
    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }


    public String getImagen() {
        return imagen;
    }

    public String getIngredientes() {
        return ingredientes;
    }

    public String getPasos() {
        return pasos;
    }

    public int getImagenResId() {
        return imagenResId;
    }

    public void setImagenResId(int imagenResId) {
        this.imagenResId = imagenResId;
    }

    public String getTipoReceta() {
        return tipoReceta;
    }
}


