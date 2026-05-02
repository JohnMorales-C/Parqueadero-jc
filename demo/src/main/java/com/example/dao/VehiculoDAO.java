package com.example.dao;

import com.example.config.ConexionDB;
import com.example.model.Vehiculo;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class VehiculoDAO {

    public void insertar(Vehiculo v) {
        try {
            Connection conn = ConexionDB.conectar();

            String sql = "INSERT INTO vehiculo(placa, id_cliente, id_tipo, id_marca, id_color, id_estado) VALUES (?,?,?,?,?,?)";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, v.getPlaca());
            ps.setInt(2, v.getIdCliente());
            ps.setInt(3, v.getIdTipo());
            ps.setInt(4, v.getIdMarca());
            ps.setInt(5, v.getIdColor());
            ps.setInt(6, v.getIdEstado());

            ps.executeUpdate();

        } catch (Exception e) {
            System.out.println("Error VehiculoDAO: " + e.getMessage());
        }
    }
}