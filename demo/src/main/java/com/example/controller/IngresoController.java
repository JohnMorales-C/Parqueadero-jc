package com.example.controller;

import com.example.service.IngresoService;
public class IngresoController {

    private IngresoService service = new IngresoService();

    public void registrarIngreso(int idVehiculo, int idEspacio, int idUsuario) {
        service.registrarIngreso(idVehiculo, idEspacio, idUsuario);
    }

    public void salida(int idVehiculo, int idTipo) {
        service.registrarSalida(idVehiculo, idTipo);
    }
}