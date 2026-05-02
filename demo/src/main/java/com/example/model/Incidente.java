package com.example.model;

import java.time.LocalDateTime;

public class Incidente {
        private int idIncidente;
    private int idIngreso;
    private String tipo;
    private String descripcion;
    private LocalDateTime fecha;

    public int getIdIncidente() { return idIncidente; }
    public void setIdIncidente(int idIncidente) { this.idIncidente = idIncidente; }

    public int getIdIngreso() { return idIngreso; }
    public void setIdIngreso(int idIngreso) { this.idIngreso = idIngreso; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
}
