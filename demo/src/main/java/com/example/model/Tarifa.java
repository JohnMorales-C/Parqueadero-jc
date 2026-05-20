package com.example.model;

/**
 * Modelo de Tarifa.
 */
public class Tarifa {

    private int idTarifa;
    private String codigoTarifa;
    private int anio;
    private int idTipo;
    private String tipoCobro; // HORA, DIA, MES
    private int horasBase;
    private double valorBase;
    private double valorHoraAdicional;
    private String estado; // VIGENTE / CADUCADA
    private String descripcion;

    public int getIdTarifa() { return idTarifa; }
    public void setIdTarifa(int idTarifa) { this.idTarifa = idTarifa; }

    public String getCodigoTarifa() { return codigoTarifa; }
    public void setCodigoTarifa(String codigoTarifa) { this.codigoTarifa = codigoTarifa; }

    public int getAnio() { return anio; }
    public void setAnio(int anio) { this.anio = anio; }

    public int getIdTipo() { return idTipo; }
    public void setIdTipo(int idTipo) { this.idTipo = idTipo; }

    public String getTipoCobro() { return tipoCobro; }
    public void setTipoCobro(String tipoCobro) { this.tipoCobro = tipoCobro; }

    public int getHorasBase() { return horasBase; }
    public void setHorasBase(int horasBase) { this.horasBase = horasBase; }

    public double getValorBase() { return valorBase; }
    public void setValorBase(double valorBase) { this.valorBase = valorBase; }

    public double getValorHoraAdicional() { return valorHoraAdicional; }
    public void setValorHoraAdicional(double valorHoraAdicional) { this.valorHoraAdicional = valorHoraAdicional; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}