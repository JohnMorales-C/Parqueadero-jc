package com.example.controller;

import com.example.model.Ingreso;
import com.example.service.IngresoService;

import java.time.LocalDateTime;
import java.util.List;

/**
 * IngresoController - Controlador para la gestión de ingresos y salidas.
 */
public class IngresoController {

    private IngresoService service = new IngresoService();

    /**
     * Registra un nuevo ingreso.
     *
     * @param ingreso Ingreso a registrar
     * @return true si la operación fue exitosa
     */
    public boolean registrarIngreso(Ingreso ingreso) {
        return service.registrarIngreso(ingreso);
    }

    /**
     * Registra la salida de un vehículo.
     *
     * @param idIngreso ID del ingreso
     * @param fechaSalida Fecha y hora de salida
     * @param total Total a pagar
     * @return true si la operación fue exitosa
     */
    public boolean registrarSalida(int idIngreso, LocalDateTime fechaSalida, double total) {
        return service.registrarSalida(idIngreso, fechaSalida, total);
    }

    /**
     * Lista todos los ingresos.
     *
     * @return Lista de ingresos
     */
    public List<Ingreso> listarIngresos() {
        return service.listarIngresos();
    }

    /**
     * Obtiene un ingreso activo por ID de vehículo.
     *
     * @param idVehiculo ID del vehículo
     * @return Ingreso activo o null
     */
    public Ingreso obtenerIngresoActivo(int idVehiculo) {
        return service.obtenerIngresoActivo(idVehiculo);
    }

    public void salida(int int1, int int2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'salida'");
    }
}