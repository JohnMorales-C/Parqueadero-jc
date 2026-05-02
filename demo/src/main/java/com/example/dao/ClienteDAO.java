package com.example.dao;
import com.example.config.ConexionDB;
import com.example.model.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement; 

public class ClienteDAO {
        public void insertar(Cliente c) {
        try {
            Connection conn = ConexionDB.conectar();

            String sql = "INSERT INTO cliente(nombre, documento, telefono, correo) VALUES (?,?,?,?)";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, c.getNombre());
            ps.setString(2, c.getDocumento());
            ps.setString(3, c.getTelefono());
            ps.setString(4, c.getCorreo());

            ps.executeUpdate();

        } catch (Exception e) {
            System.out.println("Error ClienteDAO: " + e.getMessage());
        }
    }
}
