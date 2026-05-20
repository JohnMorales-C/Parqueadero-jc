package com.example.controller;

import com.example.model.Cliente;
import com.example.service.ClienteService;

import java.util.List;

/**
 * ClienteController - Controlador para la gestión de clientes.
 * Gestiona la lógica de interacción entre la vista y el servicio.
 */
public class ClienteController {

    private ClienteService service = new ClienteService();

    /**
     * Crea un nuevo cliente.
     *
     * @param cliente Cliente a crear
     * @return true si la operación fue exitosa
     */
    public boolean crearCliente(Cliente cliente) {
        return service.registrar(cliente);
    }

    /**
     * Lista todos los clientes.
     *
     * @return Lista de clientes
     */
    public List<Cliente> listarClientes() {
        return service.listar();
    }

    /**
     * Actualiza un cliente existente.
     *
     * @param cliente Cliente con datos actualizados
     * @return true si la operación fue exitosa
     */
    public boolean actualizarCliente(Cliente cliente) {
        return service.actualizar(cliente);
    }

    /**
     * Elimina un cliente.
     *
     * @param idCliente ID del cliente a eliminar
     * @return true si la operación fue exitosa
     */
    public boolean eliminarCliente(int idCliente) {
        return service.eliminar(idCliente);
    }

    /**
     * Obtiene un cliente por ID.
     *
     * @param idCliente ID del cliente
     * @return Cliente encontrado
     */
    public Cliente obtenerCliente(int idCliente) {
        return service.obtener(idCliente);
    }
}
