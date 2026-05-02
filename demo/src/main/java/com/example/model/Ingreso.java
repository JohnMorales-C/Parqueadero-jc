package com.example.model;

import java.time.LocalDateTime;

public class Ingreso {
        private int idIngreso;
    private int idVehiculo;
    private int idEspacio;
    private int idUsuario;
    private LocalDateTime fechaEntrada;
    private LocalDateTime fechaSalida;
    private double tiempoHoras;
    private double total;
    private int idMetodo;

    public int getIdIngreso() { return idIngreso; }
    public void setIdIngreso(int idIngreso) { this.idIngreso = idIngreso; }

    public int getIdVehiculo() { return idVehiculo; }
    public void setIdVehiculo(int idVehiculo) { this.idVehiculo = idVehiculo; }

    public int getIdEspacio() { return idEspacio; }
    public void setIdEspacio(int idEspacio) { this.idEspacio = idEspacio; }

    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    public LocalDateTime getFechaEntrada() { return fechaEntrada; }
    public void setFechaEntrada(LocalDateTime fechaEntrada) { this.fechaEntrada = fechaEntrada; }

    public LocalDateTime getFechaSalida() { return fechaSalida; }
    public void setFechaSalida(LocalDateTime fechaSalida) { this.fechaSalida = fechaSalida; }

    public double getTiempoHoras() { return tiempoHoras; }
    public void setTiempoHoras(double tiempoHoras) { this.tiempoHoras = tiempoHoras; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }

    public int getIdMetodo() { return idMetodo; }
    public void setIdMetodo(int idMetodo) { this.idMetodo = idMetodo; }
}
