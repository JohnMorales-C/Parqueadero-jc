package com.example.service;

import com.example.dao.MarcaDAO;
import com.example.model.Marca;

import java.util.List;

/**
 * MarcaService - Capa de servicio para el catálogo de marcas.
 */
public class MarcaService {

    private MarcaDAO dao = new MarcaDAO();

    /**
     * Lista todas las marcas.
     *
     * @return Lista de marcas
     */
    public List<Marca> listar() {
        return dao.listar();
    }

    /**
     * Lista todas las marcas.
     * Método alias para compatibilidad.
     *
     * @return Lista de marcas
     */
    public List<Marca> listarMarcas() {
        return listar();
    }
}