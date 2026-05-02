package com.example.view;

import com.example.config.ConexionDB;
import com.example.controller.ClienteController;
import com.example.controller.VehiculoController;
import com.example.controller.IngresoController;
import com.example.controller.TarifaController;

import java.sql.Connection;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        // 🔍 VALIDAR CONEXIÓN
        try (Connection conn = ConexionDB.conectar()) {

            if (conn == null) {
                System.out.println("❌ No se pudo conectar a la base de datos");
                return;
            }

            System.out.println("✅ Conexión a la base de datos exitosa");

        } catch (Exception e) {
            System.out.println("❌ Error de conexión: " + e.getMessage());
            return;
        }

        // 👉 Si llega aquí, la conexión está OK
        Scanner sc = new Scanner(System.in);

        ClienteController clienteCtrl = new ClienteController();
        VehiculoController vehiculoCtrl = new VehiculoController();
        IngresoController ingresoCtrl = new IngresoController();
        TarifaController tarifaCtrl = new TarifaController();

        int opcion;

        do {
            System.out.println("\n===== PARQUEADERO =====");
            System.out.println("1. Registrar cliente");
            System.out.println("2. Registrar vehículo");
            System.out.println("3. Registrar ingreso");
            System.out.println("4. Registrar salida");
            System.out.println("5. Registrar tarifa");
            System.out.println("0. Salir");
            System.out.print("Seleccione: ");

            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {

                case 1:
                    System.out.print("Nombre: ");
                    String nombre = sc.nextLine();

                    System.out.print("Documento: ");
                    String doc = sc.nextLine();

                    System.out.print("Teléfono: ");
                    String tel = sc.nextLine();

                    System.out.print("Correo: ");
                    String correo = sc.nextLine();

                    clienteCtrl.crearCliente(nombre, doc, tel, correo);
                    System.out.println("✔ Cliente registrado");
                    break;

                case 2:
                    System.out.print("Placa: ");
                    String placa = sc.nextLine();

                    System.out.print("ID Cliente: ");
                    int idCliente = sc.nextInt();

                    System.out.print("ID Tipo Vehículo: ");
                    int idTipo = sc.nextInt();

                    System.out.print("ID Marca: ");
                    int idMarca = sc.nextInt();

                    System.out.print("ID Color: ");
                    int idColor = sc.nextInt();

                    System.out.print("ID Estado: ");
                    int idEstado = sc.nextInt();

                    vehiculoCtrl.crearVehiculo(placa, idCliente, idTipo, idMarca, idColor, idEstado);
                    System.out.println("✔ Vehículo registrado");
                    break;

                case 3:
                    System.out.print("ID Vehículo: ");
                    int idVehiculo = sc.nextInt();

                    System.out.print("ID Espacio: ");
                    int idEspacio = sc.nextInt();

                    System.out.print("ID Usuario: ");
                    int idUsuario = sc.nextInt();

                    ingresoCtrl.registrarIngreso(idVehiculo, idEspacio, idUsuario);
                    System.out.println("✔ Ingreso registrado");
                    break;

                case 4:
                    System.out.println("\n--- SALIDA ---");

                    System.out.print("ID Vehículo: ");
                    int vehiculoSalida = sc.nextInt();

                    System.out.print("ID Tipo Vehículo: ");
                    int tipoSalida = sc.nextInt();

                    ingresoCtrl.salida(vehiculoSalida, tipoSalida);
                    break;

                case 5:
                    System.out.println("\n--- REGISTRAR TARIFA ---");

                    System.out.print("ID Tipo Vehículo: ");
                    int tipo = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Tipo de cobro (HORA/DIA/MES): ");
                    String cobro = sc.nextLine().toUpperCase();

                    System.out.print("Valor base: ");
                    double base = sc.nextDouble();

                    System.out.print("Horas base (si es HORA, si no poner 0): ");
                    int horas = sc.nextInt();

                    System.out.print("Valor hora adicional (si aplica): ");
                    double adicional = sc.nextDouble();

                    System.out.print("Año: ");
                    int anio = sc.nextInt();

                    tarifaCtrl.registrarTarifa(tipo, cobro, base, horas, adicional, anio);

                    System.out.println("✔ Tarifa registrada");
                    break;

                case 0:
                    System.out.println("Saliendo...");
                    break;

                default:
                    System.out.println("Opción inválida");
            }

        } while (opcion != 0);

        sc.close();
    }
}
