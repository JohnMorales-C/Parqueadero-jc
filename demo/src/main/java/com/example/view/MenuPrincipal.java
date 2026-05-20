package com.example.view;

import javax.swing.*;
import java.awt.*;

public class MenuPrincipal extends JFrame {

    public MenuPrincipal() {

        setTitle("Sistema Parqueadero");
        setSize(500, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JLabel titulo = new JLabel("SISTEMA PARQUEADERO", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));

        JButton btnCliente = new JButton("Registrar Cliente");
        JButton btnVehiculo = new JButton("Registrar Vehículo");
        JButton btnIngreso = new JButton("Registrar Ingreso");
        JButton btnSalida = new JButton("Registrar Salida");
        JButton btnTarifa = new JButton("Registrar Tarifa");

        btnCliente.addActionListener(e -> {
            ClienteView vista = new ClienteView();
            vista.setVisible(true);
        });

        btnVehiculo.addActionListener(e -> {
            VehiculoView vista = new VehiculoView();
            vista.setVisible(true);
        });

        btnIngreso.addActionListener(e -> {
            IngresoView vista = new IngresoView();
            vista.setVisible(true);
        });

        btnSalida.addActionListener(e -> {
            SalidaView vista = new SalidaView();
            vista.setVisible(true);
        });

        btnTarifa.addActionListener(e -> {
            TarifaView vista = new TarifaView();
            vista.setVisible(true);
        });

        add(titulo, BorderLayout.NORTH);

        panel.add(btnCliente);
        panel.add(btnVehiculo);
        panel.add(btnIngreso);
        panel.add(btnSalida);
        panel.add(btnTarifa);

        add(panel, BorderLayout.CENTER);
    }
}