package com.example.recipesapp.models;

public class Receta implements java.io.Serializable {
    private int id;
    private String titulo;
    private String descripcion;
    private String categoria;
    private String imagen;
    private String ingredientes;
    private String pasos;
    private int imagenResId;

    public Receta(String titulo, String descripcion, String categoria, String imagen, String ingredientes, String pasos, int imagenResId) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.imagen = imagen;
        this.ingredientes = ingredientes;
        this.pasos = pasos;
        this.imagenResId = imagenResId;
    }

    public Receta(String titulo, String descripcion, int imagenResId) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.imagenResId = imagenResId;
    }

    public Receta(int id, String titulo, String descripcion, String categoria, String imagen, String ingredientes, String pasos) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.imagen = imagen;
        this.ingredientes = ingredientes;
        this.pasos = pasos;
    }
    public Receta(String titulo, String descripcion, String imagen, String ingredientes, String pasos, int imagenResId) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.categoria = ""; // Asignar vacío si no se proporciona
        this.imagen = imagen;
        this.ingredientes = ingredientes;
        this.pasos = pasos;
        this.imagenResId = imagenResId;
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

    public String getCategoria() {
        return categoria;
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
}


