package com.example.controller;

import com.example.service.TarifaService;

public class TarifaController {

    private TarifaService service = new TarifaService();

    public void registrarTarifa(int tipo, String cobro, double base, int horas, double adicional, int anio) {
        service.crearTarifa(tipo, cobro, base, horas, adicional, anio);
    }
}
