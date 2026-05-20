package com.example.view;

import javax.swing.*;
import java.awt.*;

/**
 * MainView - Ventana principal de la aplicación.
 * Contiene un panel de navegación lateral y un panel central dinámico.
 * Todas las subvistas se cargan dentro de esta ventana.
 */
public class MainView extends JFrame {

    private JPanel panelCentral;
    private ClienteView clienteView;
    private VehiculoView vehiculoView;
    private IngresoView ingresoView;
    private TarifaView tarifaView;

    public MainView() {
        setTitle("SISTEMA PARQUEADERO JC");
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);

        // Panel principal con BorderLayout
        JPanel principal = new JPanel(new BorderLayout(10, 10));
        principal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel lateral izquierdo con botones de navegación
        JPanel panelLateral = crearPanelLateral();

        // Panel central dinámico
        panelCentral = new JPanel(new BorderLayout());
        panelCentral.setBackground(Color.WHITE);
        panelCentral.setBorder(BorderFactory.createTitledBorder("Contenido"));

        principal.add(panelLateral, BorderLayout.WEST);
        principal.add(panelCentral, BorderLayout.CENTER);

        add(principal);

        // Inicializar las vistas
        inicializarVistas();
    }

    /**
     * Crea el panel lateral con los botones de navegación.
     *
     * @return Panel lateral configurado
     */
    private JPanel crearPanelLateral() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(200, getHeight()));
        panel.setBackground(new Color(52, 73, 94));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        // Título
        JLabel titulo = new JLabel("MENÚ");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(titulo);
        panel.add(Box.createVerticalStrut(20));

        // Botones de navegación
        JButton btnClientes = crearBoton("CLIENTES");
        JButton btnVehiculos = crearBoton("VEHÍCULOS");
        JButton btnIngresos = crearBoton("INGRESOS");
        JButton btnTarifas = crearBoton("TARIFAS");
        JButton btnSalir = crearBoton("SALIR");

        // Acciones de los botones
        btnClientes.addActionListener(e -> mostrarVista(clienteView));
        btnVehiculos.addActionListener(e -> mostrarVista(vehiculoView));
        btnIngresos.addActionListener(e -> mostrarVista(ingresoView));
        btnTarifas.addActionListener(e -> mostrarVista(tarifaView));
        btnSalir.addActionListener(e -> System.exit(0));

        panel.add(btnClientes);
        panel.add(Box.createVerticalStrut(10));
        panel.add(btnVehiculos);
        panel.add(Box.createVerticalStrut(10));
        panel.add(btnIngresos);
        panel.add(Box.createVerticalStrut(10));
        panel.add(btnTarifas);
        panel.add(Box.createVerticalGlue());
        panel.add(btnSalir);

        return panel;
    }

    /**
     * Crea un botón con estilo consistente.
     *
     * @param texto Texto del botón
     * @return Botón configurado
     */
    private JButton crearBoton(String texto) {
        JButton btn = new JButton(texto);
        btn.setMaximumSize(new Dimension(180, 40));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setBackground(new Color(41, 128, 185));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 12));
        btn.setFocusPainted(false);
        btn.setMargin(new Insets(10, 10, 10, 10));
        return btn;
    }

    /**
     * Inicializa todas las vistas.
     */
    private void inicializarVistas() {
        clienteView = new ClienteView();
        vehiculoView = new VehiculoView();
        ingresoView = new IngresoView();
        tarifaView = new TarifaView();

        // Mostrar la primera vista por defecto
        mostrarVista(clienteView);
    }

    /**
     * Muestra una vista en el panel central, reemplazando la anterior.
     *
     * @param vista La vista (JPanel) a mostrar
     */
    private void mostrarVista(JPanel vista) {
        panelCentral.removeAll();
        panelCentral.add(vista, BorderLayout.CENTER);
        panelCentral.revalidate();
        panelCentral.repaint();
    }
}
