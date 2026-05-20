package com.example.service;

import com.example.dao.EstadoVehiculoDAO;
import com.example.model.EstadoVehiculo;

import java.util.List;

/**
 * EstadoVehiculoService - Capa de servicio para el catálogo de estados de vehículo.
 */
public class EstadoVehiculoService {

    private EstadoVehiculoDAO dao = new EstadoVehiculoDAO();

    /**
     * Lista todos los estados de vehículo.
     *
     * @return Lista de estados
     */
    public List<EstadoVehiculo> listar() {
        return dao.listar();
    }

    /**
     * Lista todos los estados de vehículo.
     * Método alias para compatibilidad.
     *
     * @return Lista de estados
     */
    public List<EstadoVehiculo> listarEstados() {
        return listar();
    }
}
