package com.example.service;

import com.example.dao.VehiculoDAO;
import com.example.model.Vehiculo;

public class VehiculoService {

    private VehiculoDAO dao = new VehiculoDAO();

    public void registrar(String placa, int idCliente, int idTipo, int idMarca, int idColor, int idEstado) {

        Vehiculo v = new Vehiculo();

        v.setPlaca(placa);
        v.setIdCliente(idCliente);
        v.setIdTipo(idTipo);
        v.setIdMarca(idMarca);
        v.setIdColor(idColor);
        v.setIdEstado(idEstado);

        dao.insertar(v);
    }
}
