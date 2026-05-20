package com.example.model;

public class TipoVehiculo {
        private int idTipo;
    private String nombre;
    private String descripcion;

    public int getIdTipo() { return idTipo; }
    public void setIdTipo(int idTipo) { this.idTipo = idTipo; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    @Override
public String toString() {
    return nombre;
}
}
