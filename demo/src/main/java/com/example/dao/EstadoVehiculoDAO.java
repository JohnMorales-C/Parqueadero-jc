package com.example.dao;

import com.example.config.ConexionDB;
import com.example.model.EstadoVehiculo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class EstadoVehiculoDAO {

    public List<EstadoVehiculo> listar() {

        List<EstadoVehiculo> lista = new ArrayList<>();

        try {
            Connection conn = ConexionDB.conectar();

            String sql = "SELECT * FROM estado_vehiculo";
            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                EstadoVehiculo e = new EstadoVehiculo();
                e.setIdEstado(rs.getInt("id_estado"));
                e.setNombre(rs.getString("nombre"));

                lista.add(e);
            }

        } catch (Exception e) {
            System.out.println("Error EstadoVehiculoDAO: " + e.getMessage());
        }

        return lista;
    }
}
