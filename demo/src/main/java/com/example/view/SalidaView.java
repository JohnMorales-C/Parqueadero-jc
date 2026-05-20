package com.example.view;

import com.example.controller.IngresoController;

import javax.swing.*;
import java.awt.*;

public class SalidaView extends JFrame {

    private JTextField txtVehiculo;
    private JTextField txtTipo;

    private JButton btnSalida;

    private IngresoController controller = new IngresoController();

    public SalidaView() {

        setTitle("Registrar Salida");
        setSize(350,250);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3,2,10,10));
        panel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        panel.add(new JLabel("ID Vehículo:"));
        txtVehiculo = new JTextField();
        panel.add(txtVehiculo);

        panel.add(new JLabel("ID Tipo:"));
        txtTipo = new JTextField();
        panel.add(txtTipo);

        btnSalida = new JButton("Registrar Salida");
        panel.add(btnSalida);

        btnSalida.addActionListener(e -> registrarSalida());

        add(panel);
    }

    private void registrarSalida() {

        try {

            controller.salida(
                    Integer.parseInt(txtVehiculo.getText()),
                    Integer.parseInt(txtTipo.getText())
            );

            JOptionPane.showMessageDialog(this,
                    "Salida registrada correctamente");

        } catch (Exception e) {

            JOptionPane.showMessageDialog(this,
                    "Error: " + e.getMessage());
        }
    }
}
