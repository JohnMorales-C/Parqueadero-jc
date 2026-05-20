package com.example.dao;

import com.example.config.ConexionDB;
import com.example.model.Cliente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para la entidad Cliente.
 * Implementa operaciones CRUD usando procedimientos almacenados.
 */
public class ClienteDAO {

    /**
     * Inserta un nuevo cliente usando el procedimiento sp_insertar_cliente.
     *
     * @param c Cliente a insertar
     * @return true si la operación fue exitosa, false en caso contrario
     */
    public boolean insertar(Cliente c) {
        try {
            Connection conn = ConexionDB.conectar();
            if (conn == null) return false;

            String sql = "{CALL sp_insertar_cliente(?,?,?,?)}";
            CallableStatement cs = conn.prepareCall(sql);

            cs.setString(1, c.getNombre());
            cs.setString(2, c.getDocumento());
            cs.setString(3, c.getTelefono());
            cs.setString(4, c.getCorreo());

            cs.execute();
            cs.close();
            conn.close();

            System.out.println("Cliente insertado correctamente");
            return true;

        } catch (Exception e) {
            System.out.println("Error al insertar cliente: " + e.getMessage());
            return false;
        }
    }

    /**
     * Lista todos los clientes usando el procedimiento sp_listar_clientes.
     *
     * @return Lista de clientes registrados
     */
    public List<Cliente> listar() {
        List<Cliente> clientes = new ArrayList<>();

        try {
            Connection conn = ConexionDB.conectar();
            if (conn == null) return clientes;

            String sql = "{CALL sp_listar_clientes()}";
            CallableStatement cs = conn.prepareCall(sql);

            ResultSet rs = cs.executeQuery();

            while (rs.next()) {
                Cliente c = new Cliente();
                c.setIdCliente(rs.getInt("id_cliente"));
                c.setNombre(rs.getString("nombre"));
                c.setDocumento(rs.getString("documento"));
                c.setTelefono(rs.getString("telefono"));
                c.setCorreo(rs.getString("correo"));
                clientes.add(c);
            }

            rs.close();
            cs.close();
            conn.close();

        } catch (Exception e) {
            System.out.println("Error al listar clientes: " + e.getMessage());
        }

        return clientes;
    }

    /**
     * Actualiza un cliente existente usando el procedimiento sp_actualizar_cliente.
     *
     * @param c Cliente con datos actualizados
     * @return true si la operación fue exitosa, false en caso contrario
     */
    public boolean actualizar(Cliente c) {
        try {
            Connection conn = ConexionDB.conectar();
            if (conn == null) return false;

            String sql = "{CALL sp_actualizar_cliente(?,?,?,?,?)}";
            CallableStatement cs = conn.prepareCall(sql);

            cs.setInt(1, c.getIdCliente());
            cs.setString(2, c.getNombre());
            cs.setString(3, c.getDocumento());
            cs.setString(4, c.getTelefono());
            cs.setString(5, c.getCorreo());

            cs.execute();
            cs.close();
            conn.close();

            System.out.println("Cliente actualizado correctamente");
            return true;

        } catch (Exception e) {
            System.out.println("Error al actualizar cliente: " + e.getMessage());
            return false;
        }
    }

    /**
     * Elimina un cliente usando el procedimiento sp_eliminar_cliente.
     *
     * @param idCliente ID del cliente a eliminar
     * @return true si la operación fue exitosa, false en caso contrario
     */
    public boolean eliminar(int idCliente) {
        try {
            Connection conn = ConexionDB.conectar();
            if (conn == null) return false;

            String sql = "{CALL sp_eliminar_cliente(?)}";
            CallableStatement cs = conn.prepareCall(sql);

            cs.setInt(1, idCliente);

            cs.execute();
            cs.close();
            conn.close();

            System.out.println("Cliente eliminado correctamente");
            return true;

        } catch (Exception e) {
            System.out.println("Error al eliminar cliente: " + e.getMessage());
            return false;
        }
    }
}
