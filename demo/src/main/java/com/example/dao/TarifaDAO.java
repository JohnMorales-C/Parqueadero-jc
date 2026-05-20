package com.example.dao;

import com.example.config.ConexionDB;
import com.example.model.Tarifa;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para la entidad Tarifa.
 */
public class TarifaDAO {

    /**
     * Inserta una nueva tarifa.
     */
    public boolean insertar(Tarifa t) {

        try {

            Connection conn = ConexionDB.conectar();
            if (conn == null) return false;

            String sql = "{CALL sp_insertar_tarifa(?,?,?,?,?,?,?,?,?)}";

            CallableStatement cs = conn.prepareCall(sql);

            cs.setString(1, t.getCodigoTarifa());
            cs.setInt(2, t.getAnio());
            cs.setInt(3, t.getIdTipo());
            cs.setString(4, t.getTipoCobro());
            cs.setInt(5, t.getHorasBase());
            cs.setDouble(6, t.getValorBase());
            cs.setDouble(7, t.getValorHoraAdicional());
            cs.setString(8, t.getEstado());
            cs.setString(9, t.getDescripcion());

            cs.execute();

            cs.close();
            conn.close();

            System.out.println("Tarifa insertada correctamente");

            return true;

        } catch (Exception e) {

            System.out.println("Error al insertar tarifa: " + e.getMessage());

            return false;
        }
    }

    /**
     * Lista tarifas vigentes.
     */
    public List<Tarifa> listar() {

        List<Tarifa> tarifas = new ArrayList<>();

        try {

            Connection conn = ConexionDB.conectar();
            if (conn == null) return tarifas;

            String sql = """
                    SELECT * FROM tarifa
                    WHERE estado = 'VIGENTE'
                    ORDER BY anio DESC, codigo_tarifa
                    """;

            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Tarifa t = new Tarifa();

                t.setIdTarifa(rs.getInt("id_tarifa"));
                t.setCodigoTarifa(rs.getString("codigo_tarifa"));
                t.setAnio(rs.getInt("anio"));
                t.setIdTipo(rs.getInt("id_tipo"));
                t.setTipoCobro(rs.getString("tipo_cobro"));
                t.setHorasBase(rs.getInt("horas_base"));
                t.setValorBase(rs.getDouble("valor_base"));
                t.setValorHoraAdicional(rs.getDouble("valor_hora_adicional"));
                t.setEstado(rs.getString("estado"));
                t.setDescripcion(rs.getString("descripcion"));

                tarifas.add(t);
            }

            rs.close();
            ps.close();
            conn.close();

        } catch (Exception e) {
            System.out.println("Error al listar tarifas: " + e.getMessage());
        }

        return tarifas;
    }

    /**
     * Obtiene tarifa vigente.
     */
    public Tarifa obtenerTarifaVigente(int idTipo, String tipoCobro) {

        Tarifa t = null;

        try {

            Connection conn = ConexionDB.conectar();
            if (conn == null) return null;

            String sql = """
                    SELECT * FROM tarifa
                    WHERE id_tipo = ?
                    AND tipo_cobro = ?
                    AND estado = 'VIGENTE'
                    ORDER BY anio DESC
                    LIMIT 1
                    """;

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, idTipo);
            ps.setString(2, tipoCobro);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                t = new Tarifa();

                t.setIdTarifa(rs.getInt("id_tarifa"));
                t.setCodigoTarifa(rs.getString("codigo_tarifa"));
                t.setAnio(rs.getInt("anio"));
                t.setIdTipo(rs.getInt("id_tipo"));
                t.setTipoCobro(rs.getString("tipo_cobro"));
                t.setHorasBase(rs.getInt("horas_base"));
                t.setValorBase(rs.getDouble("valor_base"));
                t.setValorHoraAdicional(rs.getDouble("valor_hora_adicional"));
                t.setEstado(rs.getString("estado"));
                t.setDescripcion(rs.getString("descripcion"));
            }

            rs.close();
            ps.close();
            conn.close();

        } catch (Exception e) {
            System.out.println("Error al obtener tarifa vigente: " + e.getMessage());
        }

        return t;
    }
}