package com.example.service;

import com.example.dao.ClienteDAO;
import com.example.model.Cliente;

public class ClienteService {

    private ClienteDAO dao = new ClienteDAO();

    public void registrar(String nombre, String doc, String tel, String correo) {
        Cliente c = new Cliente();

        c.setNombre(nombre);
        c.setDocumento(doc);
        c.setTelefono(tel);
        c.setCorreo(correo);

        dao.insertar(c);
    }
}
