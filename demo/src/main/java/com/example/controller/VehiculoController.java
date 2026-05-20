package com.example.controller;

import com.example.model.Vehiculo;
import com.example.service.VehiculoService;

import java.util.List;

/**
 * VehiculoController - Controlador para la gestión de vehículos.
 * Gestiona la lógica de interacción entre la vista y el servicio.
 */
public class VehiculoController {

    private VehiculoService service = new VehiculoService();

    /**
     * Crea un nuevo vehículo.
     *
     * @param vehiculo Vehículo a crear
     * @return true si la operación fue exitosa
     */
    public boolean crearVehiculo(Vehiculo vehiculo) {
        return service.registrar(vehiculo);
    }

    /**
     * Lista todos los vehículos.
     *
     * @return Lista de vehículos
     */
    public List<Vehiculo> listarVehiculos() {
        return service.listar();
    }

    /**
     * Actualiza un vehículo existente.
     *
     * @param vehiculo Vehículo con datos actualizados
     * @return true si la operación fue exitosa
     */
    public boolean actualizarVehiculo(Vehiculo vehiculo) {
        return service.actualizar(vehiculo);
    }

    /**
     * Elimina un vehículo.
     *
     * @param idVehiculo ID del vehículo a eliminar
     * @return true si la operación fue exitosa
     */
    public boolean eliminarVehiculo(int idVehiculo) {
        return service.eliminar(idVehiculo);
    }
}
