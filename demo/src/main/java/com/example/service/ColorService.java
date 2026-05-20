package com.example.service;

import com.example.dao.ColorDAO;
import com.example.model.Color;

import java.util.List;

/**
 * ColorService - Capa de servicio para el catálogo de colores.
 */
public class ColorService {

    private ColorDAO dao = new ColorDAO();

    /**
     * Lista todos los colores.
     *
     * @return Lista de colores
     */
    public List<Color> listar() {
        return dao.listar();
    }

    /**
     * Lista todos los colores.
     * Método alias para compatibilidad.
     *
     * @return Lista de colores
     */
    public List<Color> listarColores() {
        return listar();
    }
}