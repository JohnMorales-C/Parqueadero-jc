package com.example.model;

public class MetodoPago {
        private int idMetodo;
    private String nombre;
    private String descripcion;
    private boolean activo;

    public int getIdMetodo() { return idMetodo; }
    public void setIdMetodo(int idMetodo) { this.idMetodo = idMetodo; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
}
