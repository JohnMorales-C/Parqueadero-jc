package com.example.view;

import com.example.controller.ClienteController;
import com.example.controller.VehiculoController;

import com.example.model.*;

import com.example.service.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import java.util.List;

/**
 * VehiculoView - Vista para gestión de vehículos.
 */
public class VehiculoView extends JPanel {

    private JTextField txtPlaca;

    private JComboBox<ClienteWrapper> comboCliente;
    private JComboBox<TipoVehiculoWrapper> comboTipo;
    private JComboBox<MarcaWrapper> comboMarca;
    private JComboBox<ColorWrapper> comboColor;
    private JComboBox<EstadoVehiculoWrapper> comboEstado;

    private JButton btnGuardar;
    private JButton btnLimpiar;
    private JButton btnActualizar;
    private JButton btnEliminar;
    private JButton btnRecargar;

    private JTable tablaVehiculos;
    private DefaultTableModel modeloTabla;

    private VehiculoController controller;

    private ClienteController clienteController;

    private TipoVehiculoService tipoService;
    private MarcaService marcaService;
    private ColorService colorService;
    private EstadoVehiculoService estadoService;

    private int vehiculoSeleccionado = -1;

    public VehiculoView() {

        controller = new VehiculoController();

        clienteController = new ClienteController();

        tipoService = new TipoVehiculoService();
        marcaService = new MarcaService();
        colorService = new ColorService();
        estadoService = new EstadoVehiculoService();

        setLayout(new BorderLayout(10, 10));

        setBorder(
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        );

        setBackground(java.awt.Color.WHITE);

        add(crearFormulario(), BorderLayout.NORTH);

        add(crearTabla(), BorderLayout.CENTER);

        cargarVehiculos();
    }

private JPanel crearFormulario() {

    // PANEL PRINCIPAL
    JPanel panel = new JPanel(new BorderLayout(10, 10));
    panel.setBorder(
            BorderFactory.createTitledBorder("Registrar / Actualizar Vehículo")
    );
    panel.setBackground(java.awt.Color.WHITE);

    // =========================
    // PANEL DE CAMPOS
    // =========================
    JPanel campos = new JPanel(new GridLayout(6, 2, 10, 10));
    campos.setBackground(java.awt.Color.WHITE);

    // Placa
    campos.add(new JLabel("Placa:"));
    txtPlaca = new JTextField();
    campos.add(txtPlaca);

    // Cliente
    campos.add(new JLabel("Cliente:"));
    comboCliente = new JComboBox<>();
    cargarClientes();
    campos.add(comboCliente);

    // Tipo
    campos.add(new JLabel("Tipo:"));
    comboTipo = new JComboBox<>();
    cargarTipos();
    campos.add(comboTipo);

    // Marca
    campos.add(new JLabel("Marca:"));
    comboMarca = new JComboBox<>();
    cargarMarcas();
    campos.add(comboMarca);

    // Color
    campos.add(new JLabel("Color:"));
    comboColor = new JComboBox<>();
    cargarColores();
    campos.add(comboColor);

    // Estado
    campos.add(new JLabel("Estado:"));
    comboEstado = new JComboBox<>();
    cargarEstados();
    campos.add(comboEstado);

    // =========================
    // PANEL DE BOTONES
    // =========================
    JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
    botones.setBackground(java.awt.Color.WHITE);

    btnGuardar = new JButton("Guardar");
    btnLimpiar = new JButton("Limpiar");
    btnActualizar = new JButton("Actualizar");
    btnEliminar = new JButton("Eliminar");
    btnRecargar = new JButton("Recargar");

    // Tamaño uniforme
    Dimension size = new Dimension(130, 35);

    btnGuardar.setPreferredSize(size);
    btnLimpiar.setPreferredSize(size);
    btnActualizar.setPreferredSize(size);
    btnEliminar.setPreferredSize(size);
    btnRecargar.setPreferredSize(size);

    botones.add(btnGuardar);
    botones.add(btnLimpiar);
    botones.add(btnActualizar);
    botones.add(btnEliminar);
    botones.add(btnRecargar);

    // eventos
    btnGuardar.addActionListener(e -> guardarVehiculo());
    btnLimpiar.addActionListener(e -> limpiar());
    btnActualizar.addActionListener(e -> actualizarVehiculo());
    btnEliminar.addActionListener(e -> eliminarVehiculo());
    btnRecargar.addActionListener(e -> refrescarCombos());

    // =========================
    // ARMADO FINAL
    // =========================
    panel.add(campos, BorderLayout.CENTER);
    panel.add(botones, BorderLayout.SOUTH);

    return panel;
}

    private JPanel crearTabla() {

        JPanel panel = new JPanel(new BorderLayout());

        modeloTabla = new DefaultTableModel(
                new String[]{
                        "ID",
                        "Placa",
                        "Cliente",
                        "Tipo",
                        "Marca",
                        "Color",
                        "Estado"
                },
                0
        );

        tablaVehiculos = new JTable(modeloTabla);

        tablaVehiculos.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        tablaVehiculos.getSelectionModel()
                .addListSelectionListener(e -> {

                    int fila = tablaVehiculos.getSelectedRow();

                    if (fila >= 0) {

                        vehiculoSeleccionado =
                                (int) modeloTabla.getValueAt(fila, 0);

                        txtPlaca.setText(
                                (String) modeloTabla.getValueAt(fila, 1)
                        );
                    }
                });

        JScrollPane scroll = new JScrollPane(tablaVehiculos);

        panel.add(scroll, BorderLayout.CENTER);

        return panel;
    }

    private void guardarVehiculo() {

        try {

            Vehiculo v = new Vehiculo();

            v.setPlaca(txtPlaca.getText());

            v.setIdCliente(
                    ((ClienteWrapper)
                            comboCliente.getSelectedItem()).getId()
            );

            v.setIdTipo(
                    ((TipoVehiculoWrapper)
                            comboTipo.getSelectedItem()).getId()
            );

            v.setIdMarca(
                    ((MarcaWrapper)
                            comboMarca.getSelectedItem()).getId()
            );

            v.setIdColor(
                    ((ColorWrapper)
                            comboColor.getSelectedItem()).getId()
            );

            v.setIdEstado(
                    ((EstadoVehiculoWrapper)
                            comboEstado.getSelectedItem()).getId()
            );

            boolean ok = controller.crearVehiculo(v);

            if (ok) {

                JOptionPane.showMessageDialog(
                        this,
                        "Vehículo registrado correctamente"
                );

                limpiar();

                cargarVehiculos();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Error al registrar vehículo"
                );
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error: " + e.getMessage()
            );
        }
    }

    private void actualizarVehiculo() {

        if (vehiculoSeleccionado == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Seleccione un vehículo"
            );

            return;
        }

        try {

            Vehiculo v = new Vehiculo();

            v.setIdVehiculo(vehiculoSeleccionado);

            v.setPlaca(txtPlaca.getText());

            v.setIdCliente(
                    ((ClienteWrapper)
                            comboCliente.getSelectedItem()).getId()
            );

            v.setIdTipo(
                    ((TipoVehiculoWrapper)
                            comboTipo.getSelectedItem()).getId()
            );

            v.setIdMarca(
                    ((MarcaWrapper)
                            comboMarca.getSelectedItem()).getId()
            );

            v.setIdColor(
                    ((ColorWrapper)
                            comboColor.getSelectedItem()).getId()
            );

            v.setIdEstado(
                    ((EstadoVehiculoWrapper)
                            comboEstado.getSelectedItem()).getId()
            );

            boolean ok = controller.actualizarVehiculo(v);

            if (ok) {

                JOptionPane.showMessageDialog(
                        this,
                        "Vehículo actualizado correctamente"
                );

                limpiar();

                cargarVehiculos();
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error: " + e.getMessage()
            );
        }
    }

    private void eliminarVehiculo() {

        if (vehiculoSeleccionado == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Seleccione un vehículo"
            );

            return;
        }

        int op = JOptionPane.showConfirmDialog(
                this,
                "¿Eliminar vehículo?",
                "Confirmar",
                JOptionPane.YES_NO_OPTION
        );

        if (op == JOptionPane.YES_OPTION) {

            boolean ok =
                    controller.eliminarVehiculo(vehiculoSeleccionado);

            if (ok) {

                JOptionPane.showMessageDialog(
                        this,
                        "Vehículo eliminado correctamente"
                );

                limpiar();

                cargarVehiculos();
            }
        }
    }

    private void cargarVehiculos() {

        modeloTabla.setRowCount(0);

        List<Vehiculo> lista = controller.listarVehiculos();

        for (Vehiculo v : lista) {

            Object[] fila = {
                    v.getIdVehiculo(),
                    v.getPlaca(),
                    v.getCliente(),
                    v.getTipo(),
                    v.getMarca(),
                    v.getColor(),
                    v.getEstado()
            };

            modeloTabla.addRow(fila);
        }
    }

    private void cargarClientes() {

        List<Cliente> lista =
                clienteController.listarClientes();

        for (Cliente c : lista) {

            comboCliente.addItem(
                    new ClienteWrapper(
                            c.getIdCliente(),
                            c.getNombre()
                    )
            );
        }
    }

    private void cargarTipos() {

        List<TipoVehiculo> lista = tipoService.listar();

        for (TipoVehiculo t : lista) {

            comboTipo.addItem(
                    new TipoVehiculoWrapper(
                            t.getIdTipo(),
                            t.getNombre()
                    )
            );
        }
    }

    private void cargarMarcas() {

        List<Marca> lista = marcaService.listar();

        for (Marca m : lista) {

            comboMarca.addItem(
                    new MarcaWrapper(
                            m.getIdMarca(),
                            m.getNombre()
                    )
            );
        }
    }

    private void cargarColores() {

        List<com.example.model.Color> lista =
                colorService.listar();

        for (com.example.model.Color c : lista) {

            comboColor.addItem(
                    new ColorWrapper(
                            c.getIdColor(),
                            c.getNombre()
                    )
            );
        }
    }

    private void cargarEstados() {

        List<EstadoVehiculo> lista =
                estadoService.listar();

        for (EstadoVehiculo e : lista) {

            comboEstado.addItem(
                    new EstadoVehiculoWrapper(
                            e.getIdEstado(),
                            e.getNombre()
                    )
            );
        }
    }
private boolean combosCargados = false;
private void refrescarCombos() {

    btnRecargar.setEnabled(false);

    try {

        combosCargados = false; // 🔥 permite recargar limpio

        cargarTodosLosCombos();

        JOptionPane.showMessageDialog(this,
                "Combos actualizados correctamente");

    } finally {
        btnRecargar.setEnabled(true);
    }
}

private void cargarTodosLosCombos() {

    if (combosCargados) return; // 🔥 evita duplicación

    comboCliente.removeAllItems();
    comboTipo.removeAllItems();
    comboMarca.removeAllItems();
    comboColor.removeAllItems();
    comboEstado.removeAllItems();

    cargarClientes();
    cargarTipos();
    cargarMarcas();
    cargarColores();
    cargarEstados();

    combosCargados = true;
}

    private void limpiar() {

        txtPlaca.setText("");

        vehiculoSeleccionado = -1;

        tablaVehiculos.clearSelection();
    }

    // WRAPPERS

    private static class ClienteWrapper {

        private int id;
        private String nombre;

        ClienteWrapper(int id, String nombre) {
            this.id = id;
            this.nombre = nombre;
        }

        int getId() {
            return id;
        }

        @Override
        public String toString() {
            return nombre;
        }
    }

    private static class TipoVehiculoWrapper {

        private int id;
        private String nombre;

        TipoVehiculoWrapper(int id, String nombre) {
            this.id = id;
            this.nombre = nombre;
        }

        int getId() {
            return id;
        }

        @Override
        public String toString() {
            return nombre;
        }
    }

    private static class MarcaWrapper {

        private int id;
        private String nombre;

        MarcaWrapper(int id, String nombre) {
            this.id = id;
            this.nombre = nombre;
        }

        int getId() {
            return id;
        }

        @Override
        public String toString() {
            return nombre;
        }
    }

    private static class ColorWrapper {

        private int id;
        private String nombre;

        ColorWrapper(int id, String nombre) {
            this.id = id;
            this.nombre = nombre;
        }

        int getId() {
            return id;
        }

        @Override
        public String toString() {
            return nombre;
        }
    }

    private static class EstadoVehiculoWrapper {

        private int id;
        private String nombre;

        EstadoVehiculoWrapper(int id, String nombre) {
            this.id = id;
            this.nombre = nombre;
        }

        int getId() {
            return id;
        }

        @Override
        public String toString() {
            return nombre;
        }
    }
}