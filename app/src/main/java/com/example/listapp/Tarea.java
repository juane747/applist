package com.example.listapp;

public class Tarea {
    private String titulo;
    private String descripcion;
    private String tipo;
    private boolean completada;

    public Tarea(String titulo, String descripcion, String tipo, boolean completada) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.completada = completada;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public boolean isCompletada() {
        return completada;
    }

    public void setCompletada(boolean completada) {
        this.completada = completada;
    }
}
