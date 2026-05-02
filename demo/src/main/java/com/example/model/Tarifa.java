package com.example.model;

public class Tarifa {

    private int idTarifa;
    private int idTipo;
    private String tipoCobro; // HORA, DIA, MES
    private double valorBase;
    private int horasBase;
    private double valorHoraAdicional;
    private int anio;
    private String estado; // ACTIVA / INACTIVA

    public int getIdTarifa() { return idTarifa; }
    public void setIdTarifa(int idTarifa) { this.idTarifa = idTarifa; }

    public int getIdTipo() { return idTipo; }
    public void setIdTipo(int idTipo) { this.idTipo = idTipo; }

    public String getTipoCobro() { return tipoCobro; }
    public void setTipoCobro(String tipoCobro) { this.tipoCobro = tipoCobro; }

    public double getValorBase() { return valorBase; }
    public void setValorBase(double valorBase) { this.valorBase = valorBase; }

    public int getHorasBase() { return horasBase; }
    public void setHorasBase(int horasBase) { this.horasBase = horasBase; }

    public double getValorHoraAdicional() { return valorHoraAdicional; }
    public void setValorHoraAdicional(double valorHoraAdicional) { this.valorHoraAdicional = valorHoraAdicional; }

    public int getAnio() { return anio; }
    public void setAnio(int anio) { this.anio = anio; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}