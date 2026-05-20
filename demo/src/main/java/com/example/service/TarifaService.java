package com.example.service;

import com.example.dao.TarifaDAO;
import com.example.model.Ingreso;
import com.example.model.Tarifa;

import java.time.Duration;
import java.util.List;

/**
 * TarifaService
 */
public class TarifaService {

    private TarifaDAO dao = new TarifaDAO();

    public boolean crear(Tarifa tarifa) {
        return dao.insertar(tarifa);
    }

    public List<Tarifa> listar() {
        return dao.listar();
    }

    public Tarifa obtenerTarifaVigente(int idTipo, String tipoCobro) {
        return dao.obtenerTarifaVigente(idTipo, tipoCobro);
    }

    /**
     * Calcula total a pagar.
     */
    public double calcularTotal(Ingreso ingreso, Tarifa tarifa) {

        if (ingreso.getFechaSalida() == null) {
            return 0;
        }

        long minutos = Duration.between(
                ingreso.getFechaEntrada(),
                ingreso.getFechaSalida()
        ).toMinutes();

        double horas = minutos / 60.0;

        ingreso.setTiempoHoras(horas);

        switch (tarifa.getTipoCobro()) {

            case "HORA":

                if (horas <= tarifa.getHorasBase()) {
                    return tarifa.getValorBase();
                }

                double extra = horas - tarifa.getHorasBase();

                return tarifa.getValorBase()
                        + (extra * tarifa.getValorHoraAdicional());

            case "DIA":
                return tarifa.getValorBase();

            case "MES":
                return tarifa.getValorBase();

            default:
                return 0;
        }
    }
}