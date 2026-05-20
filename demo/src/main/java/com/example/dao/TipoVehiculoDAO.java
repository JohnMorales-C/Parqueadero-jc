package com.example.dao;

import com.example.config.ConexionDB;
import com.example.model.TipoVehiculo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TipoVehiculoDAO {

    public List<TipoVehiculo> listar() {

        List<TipoVehiculo> lista = new ArrayList<>();

        try {
            Connection conn = ConexionDB.conectar();

            String sql = "SELECT * FROM tipo_vehiculo";
            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                TipoVehiculo t = new TipoVehiculo();
                t.setIdTipo(rs.getInt("id_tipo"));
                t.setNombre(rs.getString("nombre"));
                t.setDescripcion(rs.getString("descripcion"));

                lista.add(t);
            }

        } catch (Exception e) {
            System.out.println("Error TipoVehiculoDAO: " + e.getMessage());
        }

        return lista;
    }
}