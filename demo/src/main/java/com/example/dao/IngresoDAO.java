package com.example.dao;

import com.example.config.ConexionDB;
import com.example.model.Ingreso;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class IngresoDAO {

    public void registrarIngreso(Ingreso i) {
        try {
            Connection conn = ConexionDB.conectar();

            String sql = "INSERT INTO ingreso(id_vehiculo, id_espacio, id_usuario, fecha_entrada) VALUES (?,?,?,?)";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, i.getIdVehiculo());
            ps.setInt(2, i.getIdEspacio());
            ps.setInt(3, i.getIdUsuario());
            ps.setObject(4, i.getFechaEntrada());

            ps.executeUpdate();

        } catch (Exception e) {
            System.out.println("Error IngresoDAO: " + e.getMessage());
        }
    }

    public Ingreso obtenerIngresoActivo(int idVehiculo) {

    Ingreso i = null;

    try {
        Connection conn = ConexionDB.conectar();

        String sql = "SELECT * FROM ingreso WHERE id_vehiculo = ? AND fecha_salida IS NULL LIMIT 1";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, idVehiculo);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            i = new Ingreso();
            i.setIdIngreso(rs.getInt("id_ingreso"));
            i.setIdVehiculo(rs.getInt("id_vehiculo"));
            i.setFechaEntrada(rs.getTimestamp("fecha_entrada").toLocalDateTime());
        }

    } catch (Exception e) {
        System.out.println(e.getMessage());
    }

    return i;
}

public void registrarSalida(Ingreso i) {

    try {
        Connection conn = ConexionDB.conectar();

        String sql = "UPDATE ingreso SET fecha_salida=?, tiempo_horas=?, total=? WHERE id_ingreso=?";

        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setObject(1, i.getFechaSalida());
        ps.setDouble(2, i.getTiempoHoras());
        ps.setDouble(3, i.getTotal());
        ps.setInt(4, i.getIdIngreso());

        ps.executeUpdate();

    } catch (Exception e) {
        System.out.println(e.getMessage());
    }
}

public void insertar(Ingreso i) {

    try (Connection conn = ConexionDB.conectar();
         PreparedStatement ps = conn.prepareStatement(
             "INSERT INTO ingreso(id_vehiculo, id_espacio, id_usuario, fecha_entrada) VALUES (?,?,?,?)"
         )) {

        ps.setInt(1, i.getIdVehiculo());
        ps.setInt(2, i.getIdEspacio());
        ps.setInt(3, i.getIdUsuario());
        ps.setObject(4, i.getFechaEntrada());

        ps.executeUpdate();

    } catch (Exception e) {
        System.out.println("Error ingreso: " + e.getMessage());
    }
}
}
