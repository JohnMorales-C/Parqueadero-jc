package com.example.view;

import com.example.controller.ClienteController;
import com.example.model.Cliente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * ClienteView - Vista para gestión de clientes.
 * Extiende JPanel para ser embebida en MainView.
 * Proporciona funcionalidad para crear, listar, actualizar y eliminar clientes.
 */
public class ClienteView extends JPanel {

    private JTextField txtNombre;
    private JTextField txtDocumento;
    private JTextField txtTelefono;
    private JTextField txtCorreo;
    private JButton btnGuardar;
    private JButton btnLimpiar;
    private JButton btnActualizar;
    private JButton btnEliminar;
    private JTable tablaClientes;
    private DefaultTableModel modeloTabla;
    private ClienteController controller;
    private int clienteSeleccionado = -1;

    public ClienteView() {
        controller = new ClienteController();
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        setBackground(Color.WHITE);

        // Panel superior: Formulario
        add(crearPanelFormulario(), BorderLayout.NORTH);

        // Panel central: Tabla de clientes
        add(crearPanelTabla(), BorderLayout.CENTER);

        // Cargar datos iniciales
        cargarClientes();
    }

    /**
     * Crea el panel del formulario para ingresar datos de cliente.
     *
     * @return Panel con los campos de formulario
     */
    private JPanel crearPanelFormulario() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Registrar/Actualizar Cliente"));
        panel.setBackground(Color.WHITE);

        panel.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panel.add(txtNombre);

        panel.add(new JLabel("Documento:"));
        txtDocumento = new JTextField();
        panel.add(txtDocumento);

        panel.add(new JLabel("Teléfono:"));
        txtTelefono = new JTextField();
        panel.add(txtTelefono);

        panel.add(new JLabel("Correo:"));
        txtCorreo = new JTextField();
        panel.add(txtCorreo);

        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        panelBotones.setBackground(Color.WHITE);

        btnGuardar = new JButton("Guardar");
        btnLimpiar = new JButton("Limpiar");
        btnActualizar = new JButton("Actualizar");
        btnEliminar = new JButton("Eliminar");

        btnGuardar.addActionListener(e -> guardarCliente());
        btnLimpiar.addActionListener(e -> limpiar());
        btnActualizar.addActionListener(e -> actualizarCliente());
        btnEliminar.addActionListener(e -> eliminarCliente());

        panelBotones.add(btnGuardar);
        panelBotones.add(btnLimpiar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);

        panel.add(panelBotones);

        return panel;
    }

    /**
     * Crea el panel con la tabla de clientes.
     *
     * @return Panel con la tabla
     */
    private JPanel crearPanelTabla() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Clientes Registrados"));
        panel.setBackground(Color.WHITE);

        // Crear tabla
        String[] columnas = {"ID", "Nombre", "Documento", "Teléfono", "Correo"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaClientes = new JTable(modeloTabla);
        tablaClientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaClientes.getSelectionModel().addListSelectionListener(e -> cargarClienteSeleccionado());

        JScrollPane scrollPane = new JScrollPane(tablaClientes);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    /**
     * Guarda un nuevo cliente en la base de datos.
     */
    private void guardarCliente() {
        if (validarCampos()) {
            try {
                Cliente cliente = new Cliente();
                cliente.setNombre(txtNombre.getText());
                cliente.setDocumento(txtDocumento.getText());
                cliente.setTelefono(txtTelefono.getText());
                cliente.setCorreo(txtCorreo.getText());

                if (controller.crearCliente(cliente)) {
                    JOptionPane.showMessageDialog(this,
                            "Cliente registrado correctamente",
                            "Éxito",
                            JOptionPane.INFORMATION_MESSAGE);
                    limpiar();
                    cargarClientes();
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Error al registrar cliente",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                        "Error: " + e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Actualiza un cliente existente.
     */
    private void actualizarCliente() {
        if (clienteSeleccionado == -1) {
            JOptionPane.showMessageDialog(this,
                    "Seleccione un cliente de la tabla",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (validarCampos()) {
            try {
                Cliente cliente = new Cliente();
                cliente.setIdCliente(clienteSeleccionado);
                cliente.setNombre(txtNombre.getText());
                cliente.setDocumento(txtDocumento.getText());
                cliente.setTelefono(txtTelefono.getText());
                cliente.setCorreo(txtCorreo.getText());

                if (controller.actualizarCliente(cliente)) {
                    JOptionPane.showMessageDialog(this,
                            "Cliente actualizado correctamente",
                            "Éxito",
                            JOptionPane.INFORMATION_MESSAGE);
                    limpiar();
                    cargarClientes();
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Error al actualizar cliente",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                        "Error: " + e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Elimina un cliente seleccionado.
     */
    private void eliminarCliente() {
        if (clienteSeleccionado == -1) {
            JOptionPane.showMessageDialog(this,
                    "Seleccione un cliente de la tabla",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de eliminar este cliente?",
                "Confirmar Eliminación",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                if (controller.eliminarCliente(clienteSeleccionado)) {
                    JOptionPane.showMessageDialog(this,
                            "Cliente eliminado correctamente",
                            "Éxito",
                            JOptionPane.INFORMATION_MESSAGE);
                    limpiar();
                    cargarClientes();
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Error al eliminar cliente",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                        "Error: " + e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Carga los datos del cliente seleccionado en el formulario.
     */
    private void cargarClienteSeleccionado() {
        int fila = tablaClientes.getSelectedRow();
        if (fila >= 0) {
            clienteSeleccionado = (int) modeloTabla.getValueAt(fila, 0);
            txtNombre.setText((String) modeloTabla.getValueAt(fila, 1));
            txtDocumento.setText((String) modeloTabla.getValueAt(fila, 2));
            txtTelefono.setText((String) modeloTabla.getValueAt(fila, 3));
            txtCorreo.setText((String) modeloTabla.getValueAt(fila, 4));
        }
    }

    /**
     * Carga todos los clientes desde la base de datos.
     */
    private void cargarClientes() {
        modeloTabla.setRowCount(0);
        List<Cliente> clientes = controller.listarClientes();

        for (Cliente cliente : clientes) {
            Object[] fila = {
                    cliente.getIdCliente(),
                    cliente.getNombre(),
                    cliente.getDocumento(),
                    cliente.getTelefono(),
                    cliente.getCorreo()
            };
            modeloTabla.addRow(fila);
        }
    }

    /**
     * Limpia los campos del formulario.
     */
    private void limpiar() {
        txtNombre.setText("");
        txtDocumento.setText("");
        txtTelefono.setText("");
        txtCorreo.setText("");
        clienteSeleccionado = -1;
        tablaClientes.clearSelection();
    }

    /**
     * Valida que los campos requeridos no estén vacíos.
     *
     * @return true si los campos son válidos, false en caso contrario
     */
    private boolean validarCampos() {
        if (txtNombre.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre es obligatorio");
            return false;
        }
        if (txtDocumento.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El documento es obligatorio");
            return false;
        }
        return true;
    }
}
