package com.example.view;

import com.example.controller.IngresoController;
import com.example.model.Ingreso;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * IngresoView - Vista para gestión de ingresos y salidas.
 */
public class IngresoView extends JPanel {

    private JSpinner spinnerVehiculo;
    private JSpinner spinnerEspacio;
    private JSpinner spinnerUsuario;

    private JButton btnRegistrarIngreso;
    private JButton btnRegistrarSalida;

    private JTable tablaIngresos;
    private DefaultTableModel modeloTabla;

    private IngresoController controller;
    private int ingresoSeleccionado = -1;

    public IngresoView() {

        controller = new IngresoController();

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        setBackground(Color.WHITE);

        add(crearPanelFormulario(), BorderLayout.NORTH);
        add(crearPanelTabla(), BorderLayout.CENTER);

        cargarIngresos();
    }

    private JPanel crearPanelFormulario() {

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 4, 10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Registrar Ingreso / Salida"));
        panel.setBackground(Color.WHITE);

        panel.add(new JLabel("ID Vehículo:"));
        spinnerVehiculo = new JSpinner(new SpinnerNumberModel(1, 1, 9999, 1));
        panel.add(spinnerVehiculo);

        panel.add(new JLabel("ID Espacio:"));
        spinnerEspacio = new JSpinner(new SpinnerNumberModel(1, 1, 9999, 1));
        panel.add(spinnerEspacio);

        panel.add(new JLabel("ID Usuario:"));
        spinnerUsuario = new JSpinner(new SpinnerNumberModel(1, 1, 9999, 1));
        panel.add(spinnerUsuario);

        btnRegistrarIngreso = new JButton("Registrar Ingreso");
        btnRegistrarIngreso.addActionListener(e -> registrarIngreso());
        panel.add(btnRegistrarIngreso);

        btnRegistrarSalida = new JButton("Registrar Salida");
        btnRegistrarSalida.addActionListener(e -> registrarSalida());
        panel.add(btnRegistrarSalida);

        return panel;
    }

    private JPanel crearPanelTabla() {

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Ingresos Registrados"));
        panel.setBackground(Color.WHITE);

        String[] columnas = {
                "ID",
                "Vehículo",
                "Entrada",
                "Salida",
                "Tiempo Horas",
                "Total"
        };

        modeloTabla = new DefaultTableModel(columnas, 0);

        tablaIngresos = new JTable(modeloTabla);
        tablaIngresos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        tablaIngresos.getSelectionModel().addListSelectionListener(e -> {
            int fila = tablaIngresos.getSelectedRow();

            if (fila >= 0) {
                ingresoSeleccionado = (int) modeloTabla.getValueAt(fila, 0);
            }
        });

        JScrollPane scroll = new JScrollPane(tablaIngresos);

        panel.add(scroll, BorderLayout.CENTER);

        return panel;
    }

    private void registrarIngreso() {

        try {

            Ingreso ingreso = new Ingreso();

            ingreso.setIdVehiculo((int) spinnerVehiculo.getValue());
            ingreso.setIdEspacio((int) spinnerEspacio.getValue());
            ingreso.setIdUsuario((int) spinnerUsuario.getValue());

            ingreso.setFechaEntrada(LocalDateTime.now());

            boolean ok = controller.registrarIngreso(ingreso);

            if (ok) {

                JOptionPane.showMessageDialog(
                        this,
                        "Ingreso registrado correctamente"
                );

                cargarIngresos();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Error al registrar ingreso"
                );
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error: " + e.getMessage()
            );
        }
    }

    private void registrarSalida() {

        if (ingresoSeleccionado == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Seleccione un ingreso de la tabla"
            );

            return;
        }

        try {

            double total = 50000;

            boolean ok = controller.registrarSalida(
                    ingresoSeleccionado,
                    LocalDateTime.now(),
                    total
            );

            if (ok) {

                JOptionPane.showMessageDialog(
                        this,
                        "Salida registrada correctamente"
                );

                cargarIngresos();

                ingresoSeleccionado = -1;
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error: " + e.getMessage()
            );
        }
    }

    private void cargarIngresos() {

        modeloTabla.setRowCount(0);

        List<Ingreso> lista = controller.listarIngresos();

        for (Ingreso i : lista) {

            Object[] fila = {
                    i.getIdIngreso(),
                    i.getIdVehiculo(),
                    i.getFechaEntrada(),
                    i.getFechaSalida(),
                    i.getTiempoHoras(),
                    i.getTotal()
            };

            modeloTabla.addRow(fila);
        }
    }
}