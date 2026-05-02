package com.example.service;

import com.example.dao.IngresoDAO;
import com.example.dao.TarifaDAO;
import com.example.model.Ingreso;
import com.example.model.Tarifa;

import java.time.LocalDateTime;

public class IngresoService {

    private IngresoDAO ingresoDAO = new IngresoDAO();
    private TarifaDAO tarifaDAO = new TarifaDAO();
    private TarifaService tarifaService = new TarifaService();

public void registrarSalida(int idVehiculo, int idTipo) {

    // 1. Buscar ingreso activo
    Ingreso i = ingresoDAO.obtenerIngresoActivo(idVehiculo);

    if (i == null) {
        System.out.println("No hay ingreso activo");
        return;
    }

    // 2. Fecha salida
    i.setFechaSalida(LocalDateTime.now());

    // 3. Obtener tarifa vigente (por horas normalmente)
    Tarifa t = tarifaDAO.obtenerTarifaVigente(idTipo, "HORA");

    if (t == null) {
        System.out.println("No hay tarifa activa configurada");
        return;
    }

    // 4. Calcular total
    double total = tarifaService.calcularTotal(i, t);
    i.setTotal(total);

    // 5. Guardar salida
    ingresoDAO.registrarSalida(i);

    System.out.println("Salida registrada. Total: " + total);
}

public void registrarIngreso(int idVehiculo, int idEspacio, int idUsuario) {

    Ingreso i = new Ingreso();

    i.setIdVehiculo(idVehiculo);
    i.setIdEspacio(idEspacio);
    i.setIdUsuario(idUsuario);
    i.setFechaEntrada(LocalDateTime.now());

    ingresoDAO.insertar(i);

    System.out.println("Ingreso registrado correctamente");
}
}
