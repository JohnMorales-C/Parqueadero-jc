package com.example.dao;

import com.example.config.ConexionDB;
import com.example.model.Marca;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MarcaDAO {

    public List<Marca> listar() {

        List<Marca> lista = new ArrayList<>();

        try {
            Connection conn = ConexionDB.conectar();

            String sql = "SELECT * FROM marca";
            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Marca m = new Marca();
                m.setIdMarca(rs.getInt("id_marca"));
                m.setNombre(rs.getString("nombre"));

                lista.add(m);
            }

        } catch (Exception e) {
            System.out.println("Error MarcaDAO: " + e.getMessage());
        }

        return lista;
    }
}