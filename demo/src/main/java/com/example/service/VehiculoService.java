package com.example.service;

import com.example.dao.VehiculoDAO;
import com.example.model.Vehiculo;

import java.util.List;

/**
 * VehiculoService - Capa de servicio para la entidad Vehículo.
 * Orquesta la lógica de negocio entre el Controller y el DAO.
 */
public class VehiculoService {

    private VehiculoDAO dao = new VehiculoDAO();

    /**
     * Registra un nuevo vehículo.
     *
     * @param vehiculo Vehículo a registrar
     * @return true si la operación fue exitosa
     */
    public boolean registrar(Vehiculo vehiculo) {
        return dao.insertar(vehiculo);
    }

    /**
     * Lista todos los vehículos.
     *
     * @return Lista de vehículos
     */
    public List<Vehiculo> listar() {
        return dao.listar();
    }

    /**
     * Actualiza un vehículo existente.
     *
     * @param vehiculo Vehículo con datos actualizados
     * @return true si la operación fue exitosa
     */
    public boolean actualizar(Vehiculo vehiculo) {
        return dao.actualizar(vehiculo);
    }

    /**
     * Elimina un vehículo.
     *
     * @param idVehiculo ID del vehículo a eliminar
     * @return true si la operación fue exitosa
     */
    public boolean eliminar(int idVehiculo) {
        return dao.eliminar(idVehiculo);
    }
}
