package com.example.controller;

import com.example.service.ClienteService;

public class ClienteController {

    private ClienteService service = new ClienteService();

    public void crearCliente(String n, String d, String t, String c) {
        service.registrar(n, d, t, c);
    }
}
