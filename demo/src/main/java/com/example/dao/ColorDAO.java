package com.example.dao;

import com.example.config.ConexionDB;
import com.example.model.Color;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ColorDAO {

    public List<Color> listar() {

        List<Color> lista = new ArrayList<>();

        try {
            Connection conn = ConexionDB.conectar();

            String sql = "SELECT * FROM color";
            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Color c = new Color();
                c.setIdColor(rs.getInt("id_color"));
                c.setNombre(rs.getString("nombre"));

                lista.add(c);
            }

        } catch (Exception e) {
            System.out.println("Error ColorDAO: " + e.getMessage());
        }

        return lista;
    }
}
