package com.example.view;

import javax.swing.SwingUtilities;

/**
 * Punto de entrada de la aplicación.
 * Inicia la ventana principal del Sistema de Parqueadero.
 */
public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainView mainView = new MainView();
            mainView.setVisible(true);
        });
    }
}