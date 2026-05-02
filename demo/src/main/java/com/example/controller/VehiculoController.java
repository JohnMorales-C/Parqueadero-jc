package com.example.controller;

import com.example.service.VehiculoService;

public class VehiculoController {

    private VehiculoService service = new VehiculoService();

    public void crearVehiculo(String placa, int idCliente, int idTipo, int idMarca, int idColor, int idEstado) {
        service.registrar(placa, idCliente, idTipo, idMarca, idColor, idEstado);
    }
}
