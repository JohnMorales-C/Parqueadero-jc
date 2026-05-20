package com.example.service;

import com.example.dao.TipoVehiculoDAO;
import com.example.model.TipoVehiculo;

import java.util.List;

/**
 * TipoVehiculoService - Capa de servicio para el catálogo de tipos de vehículo.
 */
public class TipoVehiculoService {

    private TipoVehiculoDAO dao = new TipoVehiculoDAO();

    /**
     * Lista todos los tipos de vehículo.
     *
     * @return Lista de tipos de vehículo
     */
    public List<TipoVehiculo> listar() {
        return dao.listar();
    }

    /**
     * Lista todos los tipos de vehículo.
     * Método alias para compatibilidad.
     *
     * @return Lista de tipos de vehículo
     */
    public List<TipoVehiculo> listarTipos() {
        return listar();
    }
}