package com.example.service;

import com.example.dao.ClienteDAO;
import com.example.model.Cliente;

import java.util.List;

/**
 * ClienteService - Capa de servicio para la entidad Cliente.
 * Orquesta la lógica de negocio entre el Controller y el DAO.
 */
public class ClienteService {

    private ClienteDAO dao = new ClienteDAO();

    /**
     * Registra un nuevo cliente.
     *
     * @param cliente Cliente a registrar
     * @return true si la operación fue exitosa
     */
    public boolean registrar(Cliente cliente) {
        return dao.insertar(cliente);
    }

    /**
     * Lista todos los clientes.
     *
     * @return Lista de clientes
     */
    public List<Cliente> listar() {
        return dao.listar();
    }

    /**
     * Actualiza un cliente existente.
     *
     * @param cliente Cliente con datos actualizados
     * @return true si la operación fue exitosa
     */
    public boolean actualizar(Cliente cliente) {
        return dao.actualizar(cliente);
    }

    /**
     * Elimina un cliente.
     *
     * @param idCliente ID del cliente a eliminar
     * @return true si la operación fue exitosa
     */
    public boolean eliminar(int idCliente) {
        return dao.eliminar(idCliente);
    }

    /**
     * Obtiene un cliente por ID.
     *
     * @param idCliente ID del cliente
     * @return Cliente encontrado o null
     */
    public Cliente obtener(int idCliente) {
        List<Cliente> clientes = dao.listar();
        return clientes.stream()
                .filter(c -> c.getIdCliente() == idCliente)
                .findFirst()
                .orElse(null);
    }
}
