package com.example.dao;

import com.example.config.ConexionDB;
import com.example.model.Ingreso;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para la entidad Ingreso.
 * Implementa operaciones CRUD usando procedimientos almacenados.
 */
public class IngresoDAO {

    /**
     * Registra el ingreso de un vehículo usando sp_registrar_ingreso.
     */
    public boolean registrarIngreso(Ingreso i) {
        try {
            Connection conn = ConexionDB.conectar();
            if (conn == null) return false;

            String sql = "{CALL sp_registrar_ingreso(?,?,?,?)}";
            CallableStatement cs = conn.prepareCall(sql);

            cs.setInt(1, i.getIdVehiculo());
            cs.setInt(2, i.getIdEspacio());
            cs.setInt(3, i.getIdUsuario());
            cs.setTimestamp(4, Timestamp.valueOf(i.getFechaEntrada()));

            cs.execute();

            cs.close();
            conn.close();

            System.out.println("Ingreso registrado correctamente");
            return true;

        } catch (Exception e) {
            System.out.println("Error al registrar ingreso: " + e.getMessage());
            return false;
        }
    }

    /**
     * Registra la salida de un vehículo.
     */
    public boolean registrarSalida(int idIngreso, LocalDateTime fechaSalida, double total) {
        try {
            Connection conn = ConexionDB.conectar();
            if (conn == null) return false;

            String sql = "{CALL sp_registrar_salida(?,?,?)}";
            CallableStatement cs = conn.prepareCall(sql);

            cs.setInt(1, idIngreso);
            cs.setTimestamp(2, Timestamp.valueOf(fechaSalida));
            cs.setDouble(3, total);

            cs.execute();

            cs.close();
            conn.close();

            System.out.println("Salida registrada correctamente");
            return true;

        } catch (Exception e) {
            System.out.println("Error al registrar salida: " + e.getMessage());
            return false;
        }
    }

    /**
     * Obtiene el ingreso activo de un vehículo.
     */
    public Ingreso obtenerIngresoActivo(int idVehiculo) {

        Ingreso i = null;

        try {
            Connection conn = ConexionDB.conectar();
            if (conn == null) return null;

            String sql = "SELECT * FROM ingreso WHERE id_vehiculo = ? AND fecha_salida IS NULL LIMIT 1";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idVehiculo);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                i = new Ingreso();

                i.setIdIngreso(rs.getInt("id_ingreso"));
                i.setIdVehiculo(rs.getInt("id_vehiculo"));
                i.setIdEspacio(rs.getInt("id_espacio"));
                i.setIdUsuario(rs.getInt("id_usuario"));
                i.setFechaEntrada(
                        rs.getTimestamp("fecha_entrada").toLocalDateTime()
                );
            }

            rs.close();
            ps.close();
            conn.close();

        } catch (Exception e) {
            System.out.println("Error al obtener ingreso activo: " + e.getMessage());
        }

        return i;
    }

    /**
     * Lista todos los ingresos.
     */
    public List<Ingreso> listar() {

        List<Ingreso> ingresos = new ArrayList<>();

        try {

            Connection conn = ConexionDB.conectar();
            if (conn == null) return ingresos;

            String sql = "SELECT * FROM ingreso";

            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Ingreso i = new Ingreso();

                i.setIdIngreso(rs.getInt("id_ingreso"));
                i.setIdVehiculo(rs.getInt("id_vehiculo"));
                i.setIdEspacio(rs.getInt("id_espacio"));
                i.setIdUsuario(rs.getInt("id_usuario"));

                i.setFechaEntrada(
                        rs.getTimestamp("fecha_entrada").toLocalDateTime()
                );

                Timestamp fechaSalida = rs.getTimestamp("fecha_salida");

                if (fechaSalida != null) {
                    i.setFechaSalida(fechaSalida.toLocalDateTime());
                }

                i.setTotal(rs.getDouble("total"));

                ingresos.add(i);
            }

            rs.close();
            ps.close();
            conn.close();

        } catch (Exception e) {
            System.out.println("Error al listar ingresos: " + e.getMessage());
        }

        return ingresos;
    }
}