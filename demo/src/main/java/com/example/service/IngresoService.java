package com.example.service;

import com.example.dao.IngresoDAO;
import com.example.model.Ingreso;

import java.time.LocalDateTime;
import java.util.List;

/**
 * IngresoService - Capa de servicio para la entidad Ingreso.
 * Orquesta la lógica de negocio entre el Controller y el DAO.
 */
public class IngresoService {

    private IngresoDAO ingresoDAO = new IngresoDAO();

    /**
     * Registra un nuevo ingreso.
     *
     * @param ingreso Ingreso a registrar
     * @return true si la operación fue exitosa
     */
    public boolean registrarIngreso(Ingreso ingreso) {
        return ingresoDAO.registrarIngreso(ingreso);
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
        return ingresoDAO.registrarSalida(idIngreso, fechaSalida, total);
    }

    /**
     * Lista todos los ingresos.
     *
     * @return Lista de ingresos
     */
    public List<Ingreso> listarIngresos() {
        return ingresoDAO.listar();
    }

    /**
     * Obtiene un ingreso activo por ID de vehículo.
     *
     * @param idVehiculo ID del vehículo
     * @return Ingreso activo o null
     */
    public Ingreso obtenerIngresoActivo(int idVehiculo) {
        return ingresoDAO.obtenerIngresoActivo(idVehiculo);
    }
}
