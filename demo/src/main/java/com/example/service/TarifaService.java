package com.example.service;

import com.example.dao.TarifaDAO;
import com.example.model.Tarifa;
import com.example.model.Ingreso;

import java.time.Duration;

public class TarifaService {

    private TarifaDAO dao = new TarifaDAO();

    public void crearTarifa(int tipo, String cobro, double base, int horas, double adicional, int anio) {

        Tarifa t = new Tarifa();

        t.setIdTipo(tipo);
        t.setTipoCobro(cobro);
        t.setValorBase(base);
        t.setHorasBase(horas);
        t.setValorHoraAdicional(adicional);
        t.setAnio(anio);
        t.setEstado("ACTIVA");

        dao.insertar(t);
    }

    public double calcularTotal(Ingreso i, Tarifa t) {

        long minutos = Duration.between(i.getFechaEntrada(), i.getFechaSalida()).toMinutes();
        double horas = minutos / 60.0;

        i.setTiempoHoras(horas);

        switch (t.getTipoCobro()) {

            case "HORA":
                if (horas <= t.getHorasBase()) {
                    return t.getValorBase();
                }
                double extra = horas - t.getHorasBase();
                return t.getValorBase() + (extra * t.getValorHoraAdicional());

            case "DIA":
                return t.getValorBase(); // simplificado

            case "MES":
                return t.getValorBase(); // mensualidad fija

            default:
                return 0;
        }
    }
}