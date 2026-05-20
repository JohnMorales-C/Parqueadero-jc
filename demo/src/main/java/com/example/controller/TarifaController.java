package com.example.controller;

import com.example.model.Tarifa;
import com.example.service.TarifaService;

import java.util.List;

/**
 * TarifaController - Controlador para la gestión de tarifas.
 */
public class TarifaController {

    private TarifaService service = new TarifaService();

    /**
     * Crea una nueva tarifa.
     *
     * @param tarifa Tarifa a crear
     * @return true si la operación fue exitosa
     */
    public boolean crearTarifa(Tarifa tarifa) {
        return service.crear(tarifa);
    }

    /**
     * Lista todas las tarifas vigentes.
     *
     * @return Lista de tarifas
     */
    public List<Tarifa> listarTarifas() {
        return service.listar();
    }

    /**
     * Obtiene una tarifa vigente por tipo y tipo de cobro.
     *
     * @param idTipo ID del tipo de vehículo
     * @param tipoCobro Tipo de cobro (HORA, DIA, MES)
     * @return Tarifa vigente o null
     */
    public Tarifa obtenerTarifaVigente(int idTipo, String tipoCobro) {
        return service.obtenerTarifaVigente(idTipo, tipoCobro);
    }
}
