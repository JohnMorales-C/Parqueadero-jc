package com.example.model;

/**
 * Modelo de Vehículo.
 */
public class Vehiculo {
    private int idVehiculo;
    private String placa;
    private int idCliente;
    private int idTipo;
    private int idMarca;
    private int idColor;
    private int idEstado;
    
    // Campos para mostrar nombres (no son parte de la BD)
    private String cliente;
    private String tipo;
    private String marca;
    private String color;
    private String estado;

    public int getIdVehiculo() { return idVehiculo; }
    public void setIdVehiculo(int idVehiculo) { this.idVehiculo = idVehiculo; }

    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }

    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }

    public int getIdTipo() { return idTipo; }
    public void setIdTipo(int idTipo) { this.idTipo = idTipo; }

    public int getIdMarca() { return idMarca; }
    public void setIdMarca(int idMarca) { this.idMarca = idMarca; }

    public int getIdColor() { return idColor; }
    public void setIdColor(int idColor) { this.idColor = idColor; }

    public int getIdEstado() { return idEstado; }
    public void setIdEstado(int idEstado) { this.idEstado = idEstado; }

    public String getCliente() { return cliente; }
    public void setCliente(String cliente) { this.cliente = cliente; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}

