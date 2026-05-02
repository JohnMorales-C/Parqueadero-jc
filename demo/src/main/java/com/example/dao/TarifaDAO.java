package com.example.dao;

import com.example.config.ConexionDB;
import com.example.model.Tarifa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TarifaDAO {

    public void insertar(Tarifa t) {

        try {
            Connection conn = ConexionDB.conectar();

            String sql = "INSERT INTO tarifa(id_tipo, tipo_cobro, valor_base, horas_base, valor_hora_adicional, anio, estado) VALUES (?,?,?,?,?,?,?)";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, t.getIdTipo());
            ps.setString(2, t.getTipoCobro());
            ps.setDouble(3, t.getValorBase());
            ps.setInt(4, t.getHorasBase());
            ps.setDouble(5, t.getValorHoraAdicional());
            ps.setInt(6, t.getAnio());
            ps.setString(7, t.getEstado());

            ps.executeUpdate();

        } catch (Exception e) {
            System.out.println("Error TarifaDAO: " + e.getMessage());
        }
    }

    public Tarifa obtenerTarifaVigente(int idTipo, String tipoCobro) {

    Tarifa t = null;

    try {
        Connection conn = ConexionDB.conectar();

        String sql = "SELECT * FROM tarifa " +
                     "WHERE id_tipo=? AND tipo_cobro=? AND estado='ACTIVA' " +
                     "ORDER BY anio DESC LIMIT 1";

        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setInt(1, idTipo);
        ps.setString(2, tipoCobro);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            t = new Tarifa();
            t.setIdTarifa(rs.getInt("id_tarifa"));
            t.setIdTipo(rs.getInt("id_tipo"));
            t.setTipoCobro(rs.getString("tipo_cobro"));
            t.setValorBase(rs.getDouble("valor_base"));
            t.setHorasBase(rs.getInt("horas_base"));
            t.setValorHoraAdicional(rs.getDouble("valor_hora_adicional"));
            t.setAnio(rs.getInt("anio"));
            t.setEstado(rs.getString("estado"));
        }

    } catch (Exception e) {
        System.out.println("Error obtener tarifa: " + e.getMessage());
    }

    return t;
}
}
