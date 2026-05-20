package com.example.dao;

import com.example.config.ConexionDB;
import com.example.model.Vehiculo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para la entidad Vehículo.
 * Implementa operaciones CRUD usando procedimientos almacenados.
 */
public class VehiculoDAO {

    /**
     * Inserta un nuevo vehículo usando el procedimiento sp_insertar_vehiculo.
     *
     * @param v Vehículo a insertar
     * @return true si la operación fue exitosa, false en caso contrario
     */
    public boolean insertar(Vehiculo v) {
        try {
            Connection conn = ConexionDB.conectar();
            if (conn == null) return false;

            String sql = "{CALL sp_insertar_vehiculo(?,?,?,?,?,?)}";
            CallableStatement cs = conn.prepareCall(sql);

            cs.setString(1, v.getPlaca());
            cs.setInt(2, v.getIdCliente());
            cs.setInt(3, v.getIdTipo());
            cs.setInt(4, v.getIdMarca());
            cs.setInt(5, v.getIdColor());
            cs.setInt(6, v.getIdEstado());

            cs.execute();
            cs.close();
            conn.close();

            System.out.println("Vehículo insertado correctamente");
            return true;

        } catch (Exception e) {
            System.out.println("Error al insertar vehículo: " + e.getMessage());
            return false;
        }
    }

    /**
     * Lista todos los vehículos usando el procedimiento sp_listar_vehiculos.
     *
     * @return Lista de vehículos registrados
     */
    public List<Vehiculo> listar() {
        List<Vehiculo> vehiculos = new ArrayList<>();

        try {
            Connection conn = ConexionDB.conectar();
            if (conn == null) return vehiculos;

            String sql = "{CALL sp_listar_vehiculos()}";
            CallableStatement cs = conn.prepareCall(sql);

            ResultSet rs = cs.executeQuery();

            while (rs.next()) {
                Vehiculo v = new Vehiculo();
                v.setIdVehiculo(rs.getInt("id_vehiculo"));
                v.setPlaca(rs.getString("placa"));
                v.setCliente(rs.getString("cliente"));
                v.setTipo(rs.getString("tipo"));
                v.setMarca(rs.getString("marca"));
                v.setColor(rs.getString("color"));
                v.setEstado(rs.getString("estado"));
                vehiculos.add(v);
            }

            rs.close();
            cs.close();
            conn.close();

        } catch (Exception e) {
            System.out.println("Error al listar vehículos: " + e.getMessage());
        }

        return vehiculos;
    }

    /**
     * Actualiza un vehículo existente.
     *
     * @param v Vehículo con datos actualizados
     * @return true si la operación fue exitosa, false en caso contrario
     */
    public boolean actualizar(Vehiculo v) {
        try {
            Connection conn = ConexionDB.conectar();
            if (conn == null) return false;

            String sql = "UPDATE vehiculo SET placa=?, id_cliente=?, id_tipo=?, id_marca=?, id_color=?, id_estado=? WHERE id_vehiculo=?";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, v.getPlaca());
            ps.setInt(2, v.getIdCliente());
            ps.setInt(3, v.getIdTipo());
            ps.setInt(4, v.getIdMarca());
            ps.setInt(5, v.getIdColor());
            ps.setInt(6, v.getIdEstado());
            ps.setInt(7, v.getIdVehiculo());

            ps.executeUpdate();
            ps.close();
            conn.close();

            System.out.println("Vehículo actualizado correctamente");
            return true;

        } catch (Exception e) {
            System.out.println("Error al actualizar vehículo: " + e.getMessage());
            return false;
        }
    }

    /**
     * Elimina un vehículo.
     *
     * @param idVehiculo ID del vehículo a eliminar
     * @return true si la operación fue exitosa, false en caso contrario
     */
    public boolean eliminar(int idVehiculo) {
        try {
            Connection conn = ConexionDB.conectar();
            if (conn == null) return false;

            String sql = "DELETE FROM vehiculo WHERE id_vehiculo = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idVehiculo);

            ps.executeUpdate();
            ps.close();
            conn.close();

            System.out.println("Vehículo eliminado correctamente");
            return true;

        } catch (Exception e) {
            System.out.println("Error al eliminar vehículo: " + e.getMessage());
            return false;
        }
    }
}