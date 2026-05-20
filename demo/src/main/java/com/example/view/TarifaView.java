package com.example.view;

import com.example.controller.TarifaController;
import com.example.model.Tarifa;
import com.example.model.TipoVehiculo;
import com.example.service.TipoVehiculoService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * TarifaView - Vista para gestión de tarifas.
 */
public class TarifaView extends JPanel {

    private JTextField txtCodigoTarifa;
    private JSpinner spinnerAnio;

    private JComboBox<TipoVehiculoWrapper> comboTipo;
    private JComboBox<String> comboTipoCobro;

    private JSpinner spinnerHorasBase;

    private JTextField txtValorBase;
    private JTextField txtValorAdicional;

    private JComboBox<String> comboEstado;

    private JTextArea txtDescripcion;

    private JButton btnGuardar;
    private JButton btnLimpiar;

    private JTable tablaTarifas;
    private DefaultTableModel modeloTabla;

    private TarifaController controller;
    private TipoVehiculoService tipoService;

    public TarifaView() {

        controller = new TarifaController();
        tipoService = new TipoVehiculoService();

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        setBackground(Color.WHITE);

        add(crearFormulario(), BorderLayout.NORTH);
        add(crearTabla(), BorderLayout.CENTER);

        cargarTarifas();
    }

    private JPanel crearFormulario() {

        JPanel panel = new JPanel();

        panel.setLayout(new GridLayout(6, 2, 10, 10));

        panel.setBorder(BorderFactory.createTitledBorder("Registrar Tarifa"));

        panel.setBackground(Color.WHITE);

        panel.add(new JLabel("Código:"));
        txtCodigoTarifa = new JTextField();
        panel.add(txtCodigoTarifa);

        panel.add(new JLabel("Año:"));
        spinnerAnio = new JSpinner(new SpinnerNumberModel(2026, 2020, 2050, 1));
        panel.add(spinnerAnio);

        panel.add(new JLabel("Tipo Vehículo:"));

        comboTipo = new JComboBox<>();
        cargarTipos();

        panel.add(comboTipo);

        panel.add(new JLabel("Tipo Cobro:"));

        comboTipoCobro = new JComboBox<>(
                new String[]{"HORA", "DIA", "MES"}
        );

        panel.add(comboTipoCobro);

        panel.add(new JLabel("Horas Base:"));

        spinnerHorasBase = new JSpinner(
                new SpinnerNumberModel(2, 1, 24, 1)
        );

        panel.add(spinnerHorasBase);

        panel.add(new JLabel("Valor Base:"));

        txtValorBase = new JTextField();

        panel.add(txtValorBase);

        panel.add(new JLabel("Valor Hora Adicional:"));

        txtValorAdicional = new JTextField();

        panel.add(txtValorAdicional);

        panel.add(new JLabel("Estado:"));

        comboEstado = new JComboBox<>(
                new String[]{"VIGENTE", "CADUCADA"}
        );

        panel.add(comboEstado);

        panel.add(new JLabel("Descripción:"));

        txtDescripcion = new JTextArea(3, 20);

        panel.add(new JScrollPane(txtDescripcion));

        btnGuardar = new JButton("Guardar");
        btnLimpiar = new JButton("Limpiar");

        btnGuardar.addActionListener(e -> guardarTarifa());
        btnLimpiar.addActionListener(e -> limpiar());

        panel.add(btnGuardar);
        panel.add(btnLimpiar);

        return panel;
    }

    private JPanel crearTabla() {

        JPanel panel = new JPanel(new BorderLayout());

        panel.setBorder(
                BorderFactory.createTitledBorder("Tarifas Registradas")
        );

        modeloTabla = new DefaultTableModel(
                new String[]{
                        "ID",
                        "Código",
                        "Año",
                        "Tipo",
                        "Cobro",
                        "Base",
                        "Adicional",
                        "Estado"
                },
                0
        );

        tablaTarifas = new JTable(modeloTabla);

        JScrollPane scroll = new JScrollPane(tablaTarifas);

        panel.add(scroll, BorderLayout.CENTER);

        return panel;
    }

    private void guardarTarifa() {

        try {

            Tarifa t = new Tarifa();

            t.setCodigoTarifa(txtCodigoTarifa.getText());

            t.setAnio((int) spinnerAnio.getValue());

            t.setIdTipo(
                    ((TipoVehiculoWrapper)
                            comboTipo.getSelectedItem()).getId()
            );

            t.setTipoCobro(
                    (String) comboTipoCobro.getSelectedItem()
            );

            t.setHorasBase((int) spinnerHorasBase.getValue());

            t.setValorBase(
                    Double.parseDouble(txtValorBase.getText())
            );

            t.setValorHoraAdicional(
                    Double.parseDouble(txtValorAdicional.getText())
            );

            t.setEstado(
                    (String) comboEstado.getSelectedItem()
            );

            t.setDescripcion(txtDescripcion.getText());

            boolean ok = controller.crearTarifa(t);

            if (ok) {

                JOptionPane.showMessageDialog(
                        this,
                        "Tarifa registrada correctamente"
                );

                limpiar();

                cargarTarifas();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Error al registrar tarifa"
                );
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error: " + e.getMessage()
            );
        }
    }

    private void cargarTarifas() {

        modeloTabla.setRowCount(0);

        List<Tarifa> lista = controller.listarTarifas();

        for (Tarifa t : lista) {

            Object[] fila = {
                    t.getIdTarifa(),
                    t.getCodigoTarifa(),
                    t.getAnio(),
                    t.getIdTipo(),
                    t.getTipoCobro(),
                    t.getValorBase(),
                    t.getValorHoraAdicional(),
                    t.getEstado()
            };

            modeloTabla.addRow(fila);
        }
    }

    private void cargarTipos() {

        comboTipo.removeAllItems();

        List<TipoVehiculo> tipos = tipoService.listar();

        for (TipoVehiculo t : tipos) {

            comboTipo.addItem(
                    new TipoVehiculoWrapper(
                            t.getIdTipo(),
                            t.getNombre()
                    )
            );
        }
    }

    private void limpiar() {

        txtCodigoTarifa.setText("");
        txtValorBase.setText("");
        txtValorAdicional.setText("");
        txtDescripcion.setText("");

        spinnerAnio.setValue(2026);
        spinnerHorasBase.setValue(2);
    }

    private static class TipoVehiculoWrapper {

        private int id;
        private String nombre;

        public TipoVehiculoWrapper(int id, String nombre) {
            this.id = id;
            this.nombre = nombre;
        }

        public int getId() {
            return id;
        }

        @Override
        public String toString() {
            return nombre;
        }
    }
}