'# SISTEMA DE PARQUEADERO JC

## Datos del Proyecto

**Autores**:
- John Steban Morales Ceron
- Carlos Alberto Obando Torrente

**Grupo**: 412  
**Versión**: 1.0  
**Fecha**: Mayo de 2026

---

## Descripción del Proyecto

Este es un **Sistema de Gestión de Parqueadero** desarrollado en **Java** con arquitectura **MVC** (Model-View-Controller), conectado a una base de datos **MySQL** utilizando **JDBC puro** y **Procedimientos Almacenados**.

El sistema permite gestionar:
- **Clientes**: Registro, listado, actualización y eliminación
- **Vehículos**: Registro y gestión del parque vehicular
- **Ingresos/Salidas**: Control de entrada y salida de vehículos
- **Tarifas**: Configuración de tarifas por tipo de vehículo y período

### Tecnologías Utilizadas

| Tecnología | Versión | Propósito |
|-----------|---------|----------|
| **Java** | 17 | Lenguaje de programación principal |
| **MySQL** | 8.0+ | Base de datos relacional |
| **JDBC** | Incluido en Java | Conexión a base de datos |
| **Swing** | Incluido en Java | Interfaz gráfica de usuario |
| **Maven** | 3.6+ | Gestor de dependencias |
| **Procedimientos Almacenados** | SQL | Lógica de base de datos |
| **CallableStatement** | JDBC | Ejecución de procedures |

---

## Arquitectura MVC

El sistema implementa la arquitectura **Model-View-Controller** con capas adicionales para mayor modularidad:

```
com.example/
├── config/           → Configuración de conexión a BD
├── model/            → Clases de modelo (entidades)
├── dao/              → Data Access Objects (acceso a datos)
├── service/          → Capa de negocio (servicios)
├── controller/       → Controladores (orquestación)
└── view/             → Vistas Swing (interfaz gráfica)
```

### Capas del Proyecto

#### 1. **Model** (Modelo)
Define las entidades del sistema:
- `Cliente.java` - Información del cliente
- `Vehiculo.java` - Datos del vehículo
- `Ingreso.java` - Registro de entrada/salida
- `Tarifa.java` - Configuración de tarifas
- `TipoVehiculo.java`, `Marca.java`, `Color.java`, `EstadoVehiculo.java` - Catálogos

#### 2. **DAO** (Data Access Object)
Responsables de acceso a datos mediante **Procedimientos Almacenados**:
- `ClienteDAO.java` - Operaciones CRUD de clientes
- `VehiculoDAO.java` - Operaciones CRUD de vehículos
- `IngresoDAO.java` - Operaciones de ingreso/salida
- `TarifaDAO.java` - Operaciones de tarifas

**Importante**: Todos los DAOs utilizan **CallableStatement** para ejecutar procedimientos almacenados. **NO hay SQL directo** en los DAOs.

#### 3. **Service** (Capa de Negocio)
Orquesta la lógica de negocio:
- `ClienteService.java`
- `VehiculoService.java`
- `IngresoService.java`
- `TarifaService.java`

#### 4. **Controller** (Controlador)
Gestiona la interacción entre vista y servicio:
- `ClienteController.java`
- `VehiculoController.java`
- `IngresoController.java`
- `TarifaController.java`

#### 5. **View** (Presentación)
Interfaces gráficas con **Swing** (todas extienden **JPanel**):
- `MainView.java` - Ventana principal (única)
- `ClienteView.java` - Panel para gestión de clientes
- `VehiculoView.java` - Panel para gestión de vehículos
- `IngresoView.java` - Panel para ingreso/salida
- `TarifaView.java` - Panel para gestión de tarifas

**Nota**: La aplicación utiliza una **única ventana principal** (`MainView`) que contiene un panel central dinámico. Cuando el usuario selecciona una opción del menú, el contenido cambia sin abrir nuevas ventanas.

#### 6. **Config** (Configuración)
- `ConexionDB.java` - Gestiona la conexión a MySQL usando `DriverManager.getConnection()`

---

## Base de Datos

### Esquema de Tablas

El sistema utiliza exactamente estas 13 tablas:

```
├── cliente              → Información de clientes
├── rol                  → Roles de usuario
├── usuario              → Usuarios del sistema
├── tipo_vehiculo        → Catálogo de tipos
├── marca                → Catálogo de marcas
├── color                → Catálogo de colores
├── estado_vehiculo      → Catálogo de estados
├── vehiculo             → Datos de vehículos
├── espacio              → Espacios de parqueo
├── metodo_pago          → Métodos de pago
├── tarifa               → Configuración de tarifas
├── ingreso              → Registros de entrada/salida
└── incidente            → Registro de incidentes
```

### Relaciones Principales

```
cliente ← vehiculo → tipo_vehiculo
                  ↓ marca
                  ↓ color
                  ↓ estado_vehiculo
                  
vehiculo ← ingreso → espacio
                  ↓ usuario (rol)
                  ↓ metodo_pago
                  ↓ tarifa
```

### Llaves Foráneas

- `vehiculo.id_cliente` → `cliente.id_cliente`
- `vehiculo.id_tipo` → `tipo_vehiculo.id_tipo`
- `vehiculo.id_marca` → `marca.id_marca`
- `vehiculo.id_color` → `color.id_color`
- `vehiculo.id_estado` → `estado_vehiculo.id_estado`
- `ingreso.id_vehiculo` → `vehiculo.id_vehiculo`
- `ingreso.id_espacio` → `espacio.id_espacio`
- `ingreso.id_usuario` → `usuario.id_usuario`
- `tarifa.id_tipo` → `tipo_vehiculo.id_tipo`
- `incidente.id_ingreso` → `ingreso.id_ingreso`

---

## Procedimientos Almacenados

### CLIENTE

#### 1. sp_insertar_cliente
**Objetivo**: Registrar un nuevo cliente en el sistema.

**Parámetros**:
- `p_nombre` → nombre del cliente
- `p_documento` → documento único
- `p_telefono` → teléfono
- `p_correo` → correo electrónico

**Funcionamiento**: Inserta un nuevo registro en la tabla cliente.

```sql
CALL sp_insertar_cliente('John Doe', '123456789', '3105551234', 'john@email.com');
```

#### 2. sp_listar_clientes
**Objetivo**: Obtener todos los clientes registrados.

**Parámetros**: Ninguno

**Funcionamiento**: Devuelve todos los registros de la tabla cliente.

```sql
CALL sp_listar_clientes();
```

#### 3. sp_actualizar_cliente
**Objetivo**: Actualizar datos de un cliente existente.

**Parámetros**:
- `p_id` → ID del cliente a actualizar
- `p_nombre` → nuevo nombre
- `p_documento` → nuevo documento
- `p_telefono` → nuevo teléfono
- `p_correo` → nuevo correo

**Funcionamiento**: Modifica un cliente existente por su ID.

```sql
CALL sp_actualizar_cliente(1, 'Jane Doe', '987654321', '3105559876', 'jane@email.com');
```

#### 4. sp_eliminar_cliente
**Objetivo**: Eliminar un cliente del sistema.

**Parámetros**:
- `p_id` → ID del cliente a eliminar

**Funcionamiento**: Borra un cliente de la base de datos.

```sql
CALL sp_eliminar_cliente(1);
```

### VEHÍCULO

#### 1. sp_insertar_vehiculo
**Objetivo**: Registrar un nuevo vehículo en el sistema.

**Parámetros**:
- `p_placa` → placa del vehículo
- `p_id_cliente` → ID del cliente propietario
- `p_id_tipo` → ID del tipo de vehículo
- `p_id_marca` → ID de la marca
- `p_id_color` → ID del color
- `p_id_estado` → ID del estado del vehículo

**Funcionamiento**: Inserta un nuevo vehículo en la tabla vehiculo.

```sql
CALL sp_insertar_vehiculo('ABC123', 1, 1, 1, 1, 1);
```

#### 2. sp_listar_vehiculos
**Objetivo**: Listar todos los vehículos con información relacionada.

**Parámetros**: Ninguno

**Funcionamiento**: Devuelve vehículos con cliente, tipo, marca, color y estado mediante JOINs.

```sql
CALL sp_listar_vehiculos();
```

### INGRESO

#### 1. sp_registrar_ingreso
**Objetivo**: Registrar el ingreso de un vehículo al parqueadero.

**Parámetros**:
- `p_id_vehiculo` → ID del vehículo
- `p_id_espacio` → ID del espacio asignado
- `p_id_usuario` → ID del usuario que registra
- `p_fecha` → fecha y hora de entrada

**Funcionamiento**: Inserta un nuevo registro en la tabla ingreso.

```sql
CALL sp_registrar_ingreso(1, 1, 1, NOW());
```

#### 2. sp_registrar_salida
**Objetivo**: Registrar la salida de un vehículo del parqueadero.

**Parámetros**:
- `p_id_ingreso` → ID del ingreso a actualizar
- `p_fecha_salida` → fecha y hora de salida
- `p_total` → total a pagar calculado

**Funcionamiento**: Actualiza el registro de ingreso con la salida y total a pagar.

```sql
CALL sp_registrar_salida(1, NOW(), 50000);
```

### TARIFA

#### 1. sp_insertar_tarifa
**Objetivo**: Crear una nueva tarifa en el sistema.

**Parámetros**:
- `p_codigo` → código único de la tarifa (ej: 2026-001)
- `p_anio` → año de vigencia
- `p_id_tipo` → ID del tipo de vehículo
- `p_tipo_cobro` → tipo de cobro (HORA, DIA, MES)
- `p_horas_base` → horas incluidas en el valor base
- `p_valor_base` → valor base a pagar
- `p_valor_adicional` → valor por hora adicional
- `p_estado` → estado de la tarifa (VIGENTE, CADUCADA)
- `p_descripcion` → descripción de la tarifa

**Funcionamiento**: Inserta una nueva tarifa en la tabla tarifa.

```sql
CALL sp_insertar_tarifa('2026-001', 2026, 1, 'HORA', 2, 10000, 5000, 'VIGENTE', 'Tarifa estándar 2026');
```

---

## CallableStatement

### ¿Qué es CallableStatement?

`CallableStatement` es una interfaz de JDBC que permite ejecutar **Procedimientos Almacenados** (Stored Procedures) en la base de datos de forma segura.

### Ventajas de usar CallableStatement

1. **Seguridad**: Protege contra inyección SQL mediante preparación de consultas
2. **Rendimiento**: La lógica se ejecuta en el servidor de BD, reduciendo tráfico de red
3. **Mantenibilidad**: Cambios en la lógica no requieren recompilación de código Java
4. **Reutilización**: Múltiples aplicaciones pueden usar los mismos procedimientos
5. **Integridad**: Validaciones y reglas de negocio centralizadas

### Por qué se utilizó en este proyecto

- **JDBC puro**: Evita dependencias de frameworks ORM como Hibernate o JPA
- **Lógica centralizada**: Toda la lógica está en procedimientos, no en código Java
- **Mejor control**: Mayor control sobre el comportamiento de la base de datos
- **Educacional**: Demuestra cómo trabajar con JDBC a nivel profesional

### Ejemplo de Implementación

```java
// En ClienteDAO.java - Insertar cliente usando CallableStatement
public boolean insertar(Cliente c) {
    try {
        Connection conn = ConexionDB.conectar();
        if (conn == null) return false;

        // Llamada al procedimiento almacenado con ?
        String sql = "{CALL sp_insertar_cliente(?,?,?,?)}";
        CallableStatement cs = conn.prepareCall(sql);

        // Establecer parámetros
        cs.setString(1, c.getNombre());
        cs.setString(2, c.getDocumento());
        cs.setString(3, c.getTelefono());
        cs.setString(4, c.getCorreo());

        // Ejecutar
        cs.execute();
        cs.close();
        conn.close();

        System.out.println("Cliente insertado correctamente");
        return true;

    } catch (Exception e) {
        System.out.println("Error al insertar cliente: " + e.getMessage());
        return false;
    }
}
```

### Leer Resultados de un Procedimiento

```java
// En ClienteDAO.java - Listar clientes
public List<Cliente> listar() {
    List<Cliente> clientes = new ArrayList<>();

    try {
        Connection conn = ConexionDB.conectar();
        if (conn == null) return clientes;

        String sql = "{CALL sp_listar_clientes()}";
        CallableStatement cs = conn.prepareCall(sql);

        // Ejecutar y obtener resultados
        ResultSet rs = cs.executeQuery();

        while (rs.next()) {
            Cliente c = new Cliente();
            c.setIdCliente(rs.getInt("id_cliente"));
            c.setNombre(rs.getString("nombre"));
            c.setDocumento(rs.getString("documento"));
            c.setTelefono(rs.getString("telefono"));
            c.setCorreo(rs.getString("correo"));
            clientes.add(c);
        }

        rs.close();
        cs.close();
        conn.close();

    } catch (Exception e) {
        System.out.println("Error al listar clientes: " + e.getMessage());
    }

    return clientes;
}
```

### Patrón en Todos los DAOs

- **Insertar**: `cs.execute()`
- **Consultar**: `cs.executeQuery()` + `ResultSet`
- **Actualizar**: `cs.execute()`
- **Eliminar**: `cs.execute()`

---

## Interfaz Gráfica

### Arquitectura Visual

```
┌─────────────────────────────────────────────┐
│ SISTEMA PARQUEADERO JC                      │
├──────────┬──────────────────────────────────┤
│          │                                  │
│ CLIENTES │                                  │
│          │      PANEL CENTRAL DINÁMICO      │
│ VEHÍCULOS│      (contenido variable)        │
│          │                                  │
│ INGRESOS │                                  │
│          │                                  │
│ TARIFAS  │                                  │
│          │                                  │
│ SALIR    │                                  │
│          │                                  │
└──────────┴──────────────────────────────────┘
```

### Características Implementadas

- ✅ **Una única ventana principal** (`MainView`) - No abre múltiples JFrame
- ✅ **Panel lateral** con botones de navegación
- ✅ **Panel central dinámico** que cambia según la opción seleccionada
- ✅ **Sin ventanas emergentes** - Todas las vistas son embebidas en MainView
- ✅ **Interfaz moderna y profesional** con colores y diseño consistente
- ✅ **JPanel para todas las vistas** - No usan JFrame

### Vistas del Sistema

#### 1. ClienteView
- Formulario para registrar/actualizar clientes
- Tabla de clientes existentes
- Campos: Nombre, Documento, Teléfono, Correo
- Botones: Guardar, Limpiar, Actualizar, Eliminar

#### 2. VehiculoView
- Formulario con ComboBox para:
  - Cliente (dropdown lista de clientes)
  - Tipo de Vehículo
  - Marca
  - Color
  - Estado
- Tabla de vehículos registrados
- Botones: Guardar, Limpiar, Actualizar, Eliminar

#### 3. IngresoView
- Campos para registrar ingreso:
  - ID Vehículo (spinner)
  - ID Espacio (spinner)
  - ID Usuario (spinner)
- Botones: Registrar Ingreso, Registrar Salida
- Tabla de ingresos/salidas activas
- Cálculo automático de tiempo y total

#### 4. TarifaView
- Formulario completo para crear tarifas
- Campos:
  - Código Tarifa
  - Año (spinner)
  - Tipo Vehículo (combo)
  - Tipo Cobro (HORA, DIA, MES)
  - Horas Base (spinner)
  - Valor Base
  - Valor Hora Adicional
  - Estado (VIGENTE, CADUCADA)
  - Descripción (textarea)
- Tabla de tarifas vigentes
- Botones: Guardar, Limpiar

---

## Requisitos Técnicos

### Requisitos del Sistema

- **JDK 17** o superior
- **MySQL 8.0** o superior con base de datos creada
- **Maven 3.6** o superior

### Dependencias Maven

```xml
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
    <version>9.7.0</version>
</dependency>
```

### Configuración de Conexión

En [ConexionDB.java](demo/src/main/java/com/example/config/ConexionDB.java):

```java
private static final String URL = "jdbc:mysql://localhost:3306/parqueadero_jc";
private static final String USER = "root";
private static final String PASSWORD = "JohnMorales12";
```

---

## Instalación y Uso

### 1. Preparar la Base de Datos

Ejecutar el script completo desde [STORED_PROCEDURES.sql](demo/STORED_PROCEDURES.sql):

```bash
mysql -u root -p < STORED_PROCEDURES.sql
```

O manualmente en MySQL Workbench/Workstation copiando el contenido completo.

### 2. Compilar el Proyecto

```bash
cd demo
mvn clean compile
```

### 3. Ejecutar la Aplicación

**Opción 1**: Desde Maven
```bash
mvn exec:java -Dexec.mainClass="com.example.view.Main"
```

**Opción 2**: Desde el IDE
- Abrir el proyecto en Eclipse/IntelliJ
- Click derecho en `Main.java` → Run

### 4. Estructura del Proyecto

```
Parqueadero_jc/
├── demo/
│   ├── src/
│   │   └── main/
│   │       └── java/
│   │           └── com/example/
│   │               ├── config/
│   │               │   └── ConexionDB.java
│   │               ├── controller/
│   │               │   ├── ClienteController.java
│   │               │   ├── VehiculoController.java
│   │               │   ├── IngresoController.java
│   │               │   └── TarifaController.java
│   │               ├── dao/
│   │               │   ├── ClienteDAO.java
│   │               │   ├── VehiculoDAO.java
│   │               │   ├── IngresoDAO.java
│   │               │   ├── TarifaDAO.java
│   │               │   ├── ColorDAO.java
│   │               │   ├── MarcaDAO.java
│   │               │   ├── TipoVehiculoDAO.java
│   │               │   └── EstadoVehiculoDAO.java
│   │               ├── model/
│   │               │   ├── Cliente.java
│   │               │   ├── Vehiculo.java
│   │               │   ├── Ingreso.java
│   │               │   ├── Tarifa.java
│   │               │   ├── TipoVehiculo.java
│   │               │   ├── Marca.java
│   │               │   ├── Color.java
│   │               │   └── EstadoVehiculo.java
│   │               ├── service/
│   │               │   ├── ClienteService.java
│   │               │   ├── VehiculoService.java
│   │               │   ├── IngresoService.java
│   │               │   └── TarifaService.java
│   │               └── view/
│   │                   ├── Main.java
│   │                   ├── MainView.java
│   │                   ├── ClienteView.java
│   │                   ├── VehiculoView.java
│   │                   ├── IngresoView.java
│   │                   └── TarifaView.java
│   └── pom.xml
├── STORED_PROCEDURES.sql
└── README.md
```

---

## Funcionalidades Principales

### CRUD Completo

#### Clientes ✅
- Crear cliente
- Listar clientes
- Actualizar cliente
- Eliminar cliente
- Validación de campos obligatorios

#### Vehículos ✅
- Registrar vehículo
- Listar vehículos (con información de cliente, tipo, marca, color, estado)
- Actualizar vehículo
- Eliminar vehículo
- Selección mediante ComboBox

#### Ingresos/Salidas ✅
- Registrar ingreso de vehículo
- Registrar salida de vehículo
- Listar ingresos/salidas
- Cálculo automático de tiempo
- Cálculo de total a pagar

#### Tarifas ✅
- Crear tarifa
- Listar tarifas vigentes
- Configuración de tipos de cobro
- Cálculo automático de precios

---

## Documentación del Código

### Comentarios JavaDoc

Cada clase incluye documentación:
- Descripción de propósito
- Parámetros en métodos
- Tipo de retorno
- Excepciones posibles

**Ejemplo**:
```java
/**
 * Inserta un nuevo cliente usando el procedimiento sp_insertar_cliente.
 *
 * @param c Cliente a insertar
 * @return true si la operación fue exitosa, false en caso contrario
 */
public boolean insertar(Cliente c) {
    // Implementación...
}
```

### Patrones de Diseño

- **DAO Pattern**: Abstracción del acceso a datos
- **Service Layer**: Capa de lógica de negocio
- **MVC Pattern**: Separación clara de responsabilidades
- **Observer Pattern**: Listeners en componentes Swing

### Manejo de Excepciones

- Try-catch en todas las operaciones de BD
- Mensajes descriptivos de error
- Logging en consola
- Diálogos informativos al usuario

### Validaciones

- Campos requeridos validados antes de insertar
- Confirmación para operaciones destructivas
- Mensajes de éxito/error en la interfaz
- Prevención de valores numéricos inválidos

---

## CÓDIGOS SQL - PROCEDIMIENTOS ALMACENADOS

### CLIENTE - sp_insertar_cliente

**Objetivo**: Registrar un nuevo cliente en el sistema.

**Parámetros**:
- p_nombre → nombre del cliente
- p_documento → documento único
- p_telefono → teléfono
- p_correo → correo electrónico

**Funcionamiento**: Inserta un nuevo registro en la tabla cliente.

```sql
DELIMITER $$

CREATE PROCEDURE sp_insertar_cliente(
    IN p_nombre VARCHAR(80),
    IN p_documento VARCHAR(20),
    IN p_telefono VARCHAR(20),
    IN p_correo VARCHAR(80)
)
BEGIN
    INSERT INTO cliente(nombre, documento, telefono, correo)
    VALUES(p_nombre, p_documento, p_telefono, p_correo);
END $$

DELIMITER ;
```

---

### CLIENTE - sp_listar_clientes

**Objetivo**: Listar todos los clientes registrados.

**Parámetros**: Ninguno

**Funcionamiento**: Selecciona todos los registros de la tabla cliente.

```sql
DELIMITER $$

CREATE PROCEDURE sp_listar_clientes()
BEGIN
    SELECT * FROM cliente;
END $$

DELIMITER ;
```

---

### CLIENTE - sp_actualizar_cliente

**Objetivo**: Actualizar la información de un cliente existente.

**Parámetros**:
- p_id → ID del cliente a actualizar
- p_nombre → nuevo nombre
- p_documento → nuevo documento
- p_telefono → nuevo teléfono
- p_correo → nuevo correo

**Funcionamiento**: Actualiza un cliente por su ID.

```sql
DELIMITER $$

CREATE PROCEDURE sp_actualizar_cliente(
    IN p_id INT,
    IN p_nombre VARCHAR(80),
    IN p_documento VARCHAR(20),
    IN p_telefono VARCHAR(20),
    IN p_correo VARCHAR(80)
)
BEGIN
    UPDATE cliente
    SET
        nombre = p_nombre,
        documento = p_documento,
        telefono = p_telefono,
        correo = p_correo
    WHERE id_cliente = p_id;
END $$

DELIMITER ;
```

---

### CLIENTE - sp_eliminar_cliente

**Objetivo**: Eliminar un cliente del sistema.

**Parámetros**:
- p_id → ID del cliente a eliminar

**Funcionamiento**: Elimina un cliente por su ID.

```sql
DELIMITER $$

CREATE PROCEDURE sp_eliminar_cliente(
    IN p_id INT
)
BEGIN
    DELETE FROM cliente
    WHERE id_cliente = p_id;
END $$

DELIMITER ;
```

---

### VEHÍCULO - sp_insertar_vehiculo

**Objetivo**: Registrar un nuevo vehículo.

**Parámetros**:
- p_placa → placa del vehículo
- p_id_cliente → ID del cliente propietario
- p_id_tipo → ID del tipo de vehículo
- p_id_marca → ID de la marca
- p_id_color → ID del color
- p_id_estado → ID del estado del vehículo

**Funcionamiento**: Inserta un nuevo vehículo en la tabla vehiculo.

```sql
DELIMITER $$

CREATE PROCEDURE sp_insertar_vehiculo(
    IN p_placa VARCHAR(10),
    IN p_id_cliente INT,
    IN p_id_tipo INT,
    IN p_id_marca INT,
    IN p_id_color INT,
    IN p_id_estado INT
)
BEGIN
    INSERT INTO vehiculo(
        placa,
        id_cliente,
        id_tipo,
        id_marca,
        id_color,
        id_estado
    )
    VALUES(
        p_placa,
        p_id_cliente,
        p_id_tipo,
        p_id_marca,
        p_id_color,
        p_id_estado
    );
END $$

DELIMITER ;
```

---

### VEHÍCULO - sp_listar_vehiculos

**Objetivo**: Listar todos los vehículos con información relacionada.

**Parámetros**: Ninguno

**Funcionamiento**: Selecciona todos los vehículos con JOIN a las tablas relacionadas (cliente, tipo, marca, color, estado).

```sql
DELIMITER $$

CREATE PROCEDURE sp_listar_vehiculos()
BEGIN
    SELECT
        v.id_vehiculo,
        v.placa,
        c.nombre AS cliente,
        tv.nombre AS tipo,
        m.nombre AS marca,
        co.nombre AS color,
        ev.nombre AS estado
    FROM vehiculo v
    INNER JOIN cliente c ON v.id_cliente = c.id_cliente
    INNER JOIN tipo_vehiculo tv ON v.id_tipo = tv.id_tipo
    INNER JOIN marca m ON v.id_marca = m.id_marca
    INNER JOIN color co ON v.id_color = co.id_color
    INNER JOIN estado_vehiculo ev ON v.id_estado = ev.id_estado;
END $$

DELIMITER ;
```

---

### INGRESO - sp_registrar_ingreso

**Objetivo**: Registrar el ingreso de un vehículo al parqueadero.

**Parámetros**:
- p_id_vehiculo → ID del vehículo
- p_id_espacio → ID del espacio asignado
- p_id_usuario → ID del usuario que registra
- p_fecha → fecha y hora de entrada

**Funcionamiento**: Inserta un nuevo registro en la tabla ingreso.

```sql
DELIMITER $$

CREATE PROCEDURE sp_registrar_ingreso(
    IN p_id_vehiculo INT,
    IN p_id_espacio INT,
    IN p_id_usuario INT,
    IN p_fecha DATETIME
)
BEGIN
    INSERT INTO ingreso(
        id_vehiculo,
        id_espacio,
        id_usuario,
        fecha_entrada
    )
    VALUES(
        p_id_vehiculo,
        p_id_espacio,
        p_id_usuario,
        p_fecha
    );
END $$

DELIMITER ;
```

---

### INGRESO - sp_registrar_salida

**Objetivo**: Registrar la salida de un vehículo del parqueadero.

**Parámetros**:
- p_id_ingreso → ID del ingreso a actualizar
- p_fecha_salida → fecha y hora de salida
- p_total → total a pagar calculado

**Funcionamiento**: Actualiza el registro de ingreso con la salida y total.

```sql
DELIMITER $$

CREATE PROCEDURE sp_registrar_salida(
    IN p_id_ingreso INT,
    IN p_fecha_salida DATETIME,
    IN p_total DECIMAL(10,2)
)
BEGIN
    UPDATE ingreso
    SET
        fecha_salida = p_fecha_salida,
        total = p_total
    WHERE id_ingreso = p_id_ingreso;
END $$

DELIMITER ;
```

---

### TARIFA - sp_insertar_tarifa

**Objetivo**: Insertar una nueva tarifa en el sistema.

**Parámetros**:
- p_codigo → código único de la tarifa (ej: 2026-001)
- p_anio → año de vigencia
- p_id_tipo → ID del tipo de vehículo
- p_tipo_cobro → tipo de cobro (HORA, DIA, MES)
- p_horas_base → horas incluidas en el valor base
- p_valor_base → valor base a pagar
- p_valor_adicional → valor por hora adicional
- p_estado → estado de la tarifa (VIGENTE, CADUCADA)
- p_descripcion → descripción de la tarifa

**Funcionamiento**: Inserta una nueva tarifa en la tabla tarifa.

```sql
DELIMITER $$

CREATE PROCEDURE sp_insertar_tarifa(
    IN p_codigo VARCHAR(20),
    IN p_anio INT,
    IN p_id_tipo INT,
    IN p_tipo_cobro VARCHAR(10),
    IN p_horas_base INT,
    IN p_valor_base DECIMAL(10,2),
    IN p_valor_adicional DECIMAL(10,2),
    IN p_estado VARCHAR(20),
    IN p_descripcion VARCHAR(150)
)
BEGIN
    INSERT INTO tarifa(
        codigo_tarifa,
        anio,
        id_tipo,
        tipo_cobro,
        horas_base,
        valor_base,
        valor_hora_adicional,
        estado,
        descripcion
    )
    VALUES(
        p_codigo,
        p_anio,
        p_id_tipo,
        p_tipo_cobro,
        p_horas_base,
        p_valor_base,
        p_valor_adicional,
        p_estado,
        p_descripcion
    );
END $$

DELIMITER ;
```

---

## CLASES JAVA - DATA ACCESS OBJECTS (DAO)

### ClienteDAO.java

**Ubicación**: `demo/src/main/java/com/example/dao/ClienteDAO.java`

**Descripción**: DAO para la entidad Cliente. Implementa operaciones CRUD usando procedimientos almacenados.

```java
package com.example.dao;

import com.example.config.ConexionDB;
import com.example.model.Cliente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para la entidad Cliente.
 * Implementa operaciones CRUD usando procedimientos almacenados.
 */
public class ClienteDAO {

    /**
     * Inserta un nuevo cliente usando el procedimiento sp_insertar_cliente.
     *
     * @param c Cliente a insertar
     * @return true si la operación fue exitosa, false en caso contrario
     */
    public boolean insertar(Cliente c) {
        try {
            Connection conn = ConexionDB.conectar();
            if (conn == null) return false;

            String sql = "{CALL sp_insertar_cliente(?,?,?,?)}";
            CallableStatement cs = conn.prepareCall(sql);

            cs.setString(1, c.getNombre());
            cs.setString(2, c.getDocumento());
            cs.setString(3, c.getTelefono());
            cs.setString(4, c.getCorreo());

            cs.execute();
            cs.close();
            conn.close();

            System.out.println("Cliente insertado correctamente");
            return true;

        } catch (Exception e) {
            System.out.println("Error al insertar cliente: " + e.getMessage());
            return false;
        }
    }

    /**
     * Lista todos los clientes usando el procedimiento sp_listar_clientes.
     *
     * @return Lista de clientes registrados
     */
    public List<Cliente> listar() {
        List<Cliente> clientes = new ArrayList<>();

        try {
            Connection conn = ConexionDB.conectar();
            if (conn == null) return clientes;

            String sql = "{CALL sp_listar_clientes()}";
            CallableStatement cs = conn.prepareCall(sql);

            ResultSet rs = cs.executeQuery();

            while (rs.next()) {
                Cliente c = new Cliente();
                c.setIdCliente(rs.getInt("id_cliente"));
                c.setNombre(rs.getString("nombre"));
                c.setDocumento(rs.getString("documento"));
                c.setTelefono(rs.getString("telefono"));
                c.setCorreo(rs.getString("correo"));
                clientes.add(c);
            }

            rs.close();
            cs.close();
            conn.close();

        } catch (Exception e) {
            System.out.println("Error al listar clientes: " + e.getMessage());
        }

        return clientes;
    }

    /**
     * Actualiza un cliente existente usando el procedimiento sp_actualizar_cliente.
     *
     * @param c Cliente con datos actualizados
     * @return true si la operación fue exitosa, false en caso contrario
     */
    public boolean actualizar(Cliente c) {
        try {
            Connection conn = ConexionDB.conectar();
            if (conn == null) return false;

            String sql = "{CALL sp_actualizar_cliente(?,?,?,?,?)}";
            CallableStatement cs = conn.prepareCall(sql);

            cs.setInt(1, c.getIdCliente());
            cs.setString(2, c.getNombre());
            cs.setString(3, c.getDocumento());
            cs.setString(4, c.getTelefono());
            cs.setString(5, c.getCorreo());

            cs.execute();
            cs.close();
            conn.close();

            System.out.println("Cliente actualizado correctamente");
            return true;

        } catch (Exception e) {
            System.out.println("Error al actualizar cliente: " + e.getMessage());
            return false;
        }
    }

    /**
     * Elimina un cliente usando el procedimiento sp_eliminar_cliente.
     *
     * @param idCliente ID del cliente a eliminar
     * @return true si la operación fue exitosa, false en caso contrario
     */
    public boolean eliminar(int idCliente) {
        try {
            Connection conn = ConexionDB.conectar();
            if (conn == null) return false;

            String sql = "{CALL sp_eliminar_cliente(?)}";
            CallableStatement cs = conn.prepareCall(sql);

            cs.setInt(1, idCliente);

            cs.execute();
            cs.close();
            conn.close();

            System.out.println("Cliente eliminado correctamente");
            return true;

        } catch (Exception e) {
            System.out.println("Error al eliminar cliente: " + e.getMessage());
            return false;
        }
    }
}
```

---

### VehiculoDAO.java

**Ubicación**: `demo/src/main/java/com/example/dao/VehiculoDAO.java`

**Descripción**: DAO para la entidad Vehículo. Implementa operaciones CRUD usando procedimientos almacenados para inserción y listado.

```java
package com.example.dao;

import com.example.config.ConexionDB;
import com.example.model.Vehiculo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para la entidad Vehículo.
 * Implementa operaciones CRUD usando procedimientos almacenados.
 */
public class VehiculoDAO {

    /**
     * Inserta un nuevo vehículo usando el procedimiento sp_insertar_vehiculo.
     *
     * @param v Vehículo a insertar
     * @return true si la operación fue exitosa, false en caso contrario
     */
    public boolean insertar(Vehiculo v) {
        try {
            Connection conn = ConexionDB.conectar();
            if (conn == null) return false;

            String sql = "{CALL sp_insertar_vehiculo(?,?,?,?,?,?)}";
            CallableStatement cs = conn.prepareCall(sql);

            cs.setString(1, v.getPlaca());
            cs.setInt(2, v.getIdCliente());
            cs.setInt(3, v.getIdTipo());
            cs.setInt(4, v.getIdMarca());
            cs.setInt(5, v.getIdColor());
            cs.setInt(6, v.getIdEstado());

            cs.execute();
            cs.close();
            conn.close();

            System.out.println("Vehículo insertado correctamente");
            return true;

        } catch (Exception e) {
            System.out.println("Error al insertar vehículo: " + e.getMessage());
            return false;
        }
    }

    /**
     * Lista todos los vehículos usando el procedimiento sp_listar_vehiculos.
     *
     * @return Lista de vehículos registrados
     */
    public List<Vehiculo> listar() {
        List<Vehiculo> vehiculos = new ArrayList<>();

        try {
            Connection conn = ConexionDB.conectar();
            if (conn == null) return vehiculos;

            String sql = "{CALL sp_listar_vehiculos()}";
            CallableStatement cs = conn.prepareCall(sql);

            ResultSet rs = cs.executeQuery();

            while (rs.next()) {
                Vehiculo v = new Vehiculo();
                v.setIdVehiculo(rs.getInt("id_vehiculo"));
                v.setPlaca(rs.getString("placa"));
                v.setCliente(rs.getString("cliente"));
                v.setTipo(rs.getString("tipo"));
                v.setMarca(rs.getString("marca"));
                v.setColor(rs.getString("color"));
                v.setEstado(rs.getString("estado"));
                vehiculos.add(v);
            }

            rs.close();
            cs.close();
            conn.close();

        } catch (Exception e) {
            System.out.println("Error al listar vehículos: " + e.getMessage());
        }

        return vehiculos;
    }

    /**
     * Actualiza un vehículo existente.
     *
     * @param v Vehículo con datos actualizados
     * @return true si la operación fue exitosa, false en caso contrario
     */
    public boolean actualizar(Vehiculo v) {
        try {
            Connection conn = ConexionDB.conectar();
            if (conn == null) return false;

            String sql = "UPDATE vehiculo SET placa=?, id_cliente=?, id_tipo=?, id_marca=?, id_color=?, id_estado=? WHERE id_vehiculo=?";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, v.getPlaca());
            ps.setInt(2, v.getIdCliente());
            ps.setInt(3, v.getIdTipo());
            ps.setInt(4, v.getIdMarca());
            ps.setInt(5, v.getIdColor());
            ps.setInt(6, v.getIdEstado());
            ps.setInt(7, v.getIdVehiculo());

            ps.executeUpdate();
            ps.close();
            conn.close();

            System.out.println("Vehículo actualizado correctamente");
            return true;

        } catch (Exception e) {
            System.out.println("Error al actualizar vehículo: " + e.getMessage());
            return false;
        }
    }

    /**
     * Elimina un vehículo.
     *
     * @param idVehiculo ID del vehículo a eliminar
     * @return true si la operación fue exitosa, false en caso contrario
     */
    public boolean eliminar(int idVehiculo) {
        try {
            Connection conn = ConexionDB.conectar();
            if (conn == null) return false;

            String sql = "DELETE FROM vehiculo WHERE id_vehiculo = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idVehiculo);

            ps.executeUpdate();
            ps.close();
            conn.close();

            System.out.println("Vehículo eliminado correctamente");
            return true;

        } catch (Exception e) {
            System.out.println("Error al eliminar vehículo: " + e.getMessage());
            return false;
        }
    }
}
```

---

### IngresoDAO.java

**Ubicación**: `demo/src/main/java/com/example/dao/IngresoDAO.java`

**Descripción**: DAO para la entidad Ingreso. Implementa operaciones para registrar entrada y salida de vehículos usando procedimientos almacenados.

```java
package com.example.dao;

import com.example.config.ConexionDB;
import com.example.model.Ingreso;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para la entidad Ingreso.
 * Implementa operaciones CRUD usando procedimientos almacenados.
 */
public class IngresoDAO {

    /**
     * Registra el ingreso de un vehículo usando sp_registrar_ingreso.
     * @param i Ingreso con datos de entrada
     * @return true si la operación fue exitosa, false en caso contrario
     */
    public boolean registrarIngreso(Ingreso i) {
        try {
            Connection conn = ConexionDB.conectar();
            if (conn == null) return false;

            String sql = "{CALL sp_registrar_ingreso(?,?,?,?)}";
            CallableStatement cs = conn.prepareCall(sql);

            cs.setInt(1, i.getIdVehiculo());
            cs.setInt(2, i.getIdEspacio());
            cs.setInt(3, i.getIdUsuario());
            cs.setTimestamp(4, Timestamp.valueOf(i.getFechaEntrada()));

            cs.execute();

            cs.close();
            conn.close();

            System.out.println("Ingreso registrado correctamente");
            return true;

        } catch (Exception e) {
            System.out.println("Error al registrar ingreso: " + e.getMessage());
            return false;
        }
    }

    /**
     * Registra la salida de un vehículo usando sp_registrar_salida.
     * @param idIngreso ID del ingreso a actualizar
     * @param fechaSalida Fecha y hora de salida
     * @param total Total a pagar
     * @return true si la operación fue exitosa, false en caso contrario
     */
    public boolean registrarSalida(int idIngreso, LocalDateTime fechaSalida, double total) {
        try {
            Connection conn = ConexionDB.conectar();
            if (conn == null) return false;

            String sql = "{CALL sp_registrar_salida(?,?,?)}";
            CallableStatement cs = conn.prepareCall(sql);

            cs.setInt(1, idIngreso);
            cs.setTimestamp(2, Timestamp.valueOf(fechaSalida));
            cs.setDouble(3, total);

            cs.execute();

            cs.close();
            conn.close();

            System.out.println("Salida registrada correctamente");
            return true;

        } catch (Exception e) {
            System.out.println("Error al registrar salida: " + e.getMessage());
            return false;
        }
    }

    /**
     * Obtiene el ingreso activo de un vehículo.
     * @param idVehiculo ID del vehículo
     * @return Ingreso activo o null si no existe
     */
    public Ingreso obtenerIngresoActivo(int idVehiculo) {

        Ingreso i = null;

        try {
            Connection conn = ConexionDB.conectar();
            if (conn == null) return null;

            String sql = "SELECT * FROM ingreso WHERE id_vehiculo = ? AND fecha_salida IS NULL LIMIT 1";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idVehiculo);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                i = new Ingreso();

                i.setIdIngreso(rs.getInt("id_ingreso"));
                i.setIdVehiculo(rs.getInt("id_vehiculo"));
                i.setIdEspacio(rs.getInt("id_espacio"));
                i.setIdUsuario(rs.getInt("id_usuario"));
                i.setFechaEntrada(
                        rs.getTimestamp("fecha_entrada").toLocalDateTime()
                );
            }

            rs.close();
            ps.close();
            conn.close();

        } catch (Exception e) {
            System.out.println("Error al obtener ingreso activo: " + e.getMessage());
        }

        return i;
    }

    /**
     * Lista todos los ingresos registrados.
     * @return Lista de ingresos
     */
    public List<Ingreso> listar() {

        List<Ingreso> ingresos = new ArrayList<>();

        try {

            Connection conn = ConexionDB.conectar();
            if (conn == null) return ingresos;

            String sql = "SELECT * FROM ingreso";

            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Ingreso i = new Ingreso();

                i.setIdIngreso(rs.getInt("id_ingreso"));
                i.setIdVehiculo(rs.getInt("id_vehiculo"));
                i.setIdEspacio(rs.getInt("id_espacio"));
                i.setIdUsuario(rs.getInt("id_usuario"));

                i.setFechaEntrada(
                        rs.getTimestamp("fecha_entrada").toLocalDateTime()
                );

                Timestamp fechaSalida = rs.getTimestamp("fecha_salida");

                if (fechaSalida != null) {
                    i.setFechaSalida(fechaSalida.toLocalDateTime());
                }

                i.setTotal(rs.getDouble("total"));

                ingresos.add(i);
            }

            rs.close();
            ps.close();
            conn.close();

        } catch (Exception e) {
            System.out.println("Error al listar ingresos: " + e.getMessage());
        }

        return ingresos;
    }
}
```

---

### TarifaDAO.java

**Ubicación**: `demo/src/main/java/com/example/dao/TarifaDAO.java`

**Descripción**: DAO para la entidad Tarifa. Implementa operaciones para crear y consultar tarifas usando procedimientos almacenados.

```java
package com.example.dao;

import com.example.config.ConexionDB;
import com.example.model.Tarifa;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para la entidad Tarifa.
 */
public class TarifaDAO {

    /**
     * Inserta una nueva tarifa usando sp_insertar_tarifa.
     * @param t Tarifa a insertar
     * @return true si la operación fue exitosa, false en caso contrario
     */
    public boolean insertar(Tarifa t) {

        try {

            Connection conn = ConexionDB.conectar();
            if (conn == null) return false;

            String sql = "{CALL sp_insertar_tarifa(?,?,?,?,?,?,?,?,?)}";

            CallableStatement cs = conn.prepareCall(sql);

            cs.setString(1, t.getCodigoTarifa());
            cs.setInt(2, t.getAnio());
            cs.setInt(3, t.getIdTipo());
            cs.setString(4, t.getTipoCobro());
            cs.setInt(5, t.getHorasBase());
            cs.setDouble(6, t.getValorBase());
            cs.setDouble(7, t.getValorHoraAdicional());
            cs.setString(8, t.getEstado());
            cs.setString(9, t.getDescripcion());

            cs.execute();

            cs.close();
            conn.close();

            System.out.println("Tarifa insertada correctamente");

            return true;

        } catch (Exception e) {

            System.out.println("Error al insertar tarifa: " + e.getMessage());

            return false;
        }
    }

    /**
     * Lista todas las tarifas vigentes.
     * @return Lista de tarifas vigentes
     */
    public List<Tarifa> listar() {

        List<Tarifa> tarifas = new ArrayList<>();

        try {

            Connection conn = ConexionDB.conectar();
            if (conn == null) return tarifas;

            String sql = """
                    SELECT * FROM tarifa
                    WHERE estado = 'VIGENTE'
                    ORDER BY anio DESC, codigo_tarifa
                    """;

            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Tarifa t = new Tarifa();

                t.setIdTarifa(rs.getInt("id_tarifa"));
                t.setCodigoTarifa(rs.getString("codigo_tarifa"));
                t.setAnio(rs.getInt("anio"));
                t.setIdTipo(rs.getInt("id_tipo"));
                t.setTipoCobro(rs.getString("tipo_cobro"));
                t.setHorasBase(rs.getInt("horas_base"));
                t.setValorBase(rs.getDouble("valor_base"));
                t.setValorHoraAdicional(rs.getDouble("valor_hora_adicional"));
                t.setEstado(rs.getString("estado"));
                t.setDescripcion(rs.getString("descripcion"));

                tarifas.add(t);
            }

            rs.close();
            ps.close();
            conn.close();

        } catch (Exception e) {
            System.out.println("Error al listar tarifas: " + e.getMessage());
        }

        return tarifas;
    }

    /**
     * Obtiene la tarifa vigente para un tipo de vehículo y tipo de cobro.
     * @param idTipo ID del tipo de vehículo
     * @param tipoCobro Tipo de cobro (HORA, DIA, MES)
     * @return Tarifa vigente o null si no existe
     */
    public Tarifa obtenerTarifaVigente(int idTipo, String tipoCobro) {

        Tarifa t = null;

        try {

            Connection conn = ConexionDB.conectar();
            if (conn == null) return null;

            String sql = """
                    SELECT * FROM tarifa
                    WHERE id_tipo = ?
                    AND tipo_cobro = ?
                    AND estado = 'VIGENTE'
                    ORDER BY anio DESC
                    LIMIT 1
                    """;

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, idTipo);
            ps.setString(2, tipoCobro);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                t = new Tarifa();

                t.setIdTarifa(rs.getInt("id_tarifa"));
                t.setCodigoTarifa(rs.getString("codigo_tarifa"));
                t.setAnio(rs.getInt("anio"));
                t.setIdTipo(rs.getInt("id_tipo"));
                t.setTipoCobro(rs.getString("tipo_cobro"));
                t.setHorasBase(rs.getInt("horas_base"));
                t.setValorBase(rs.getDouble("valor_base"));
                t.setValorHoraAdicional(rs.getDouble("valor_hora_adicional"));
                t.setEstado(rs.getString("estado"));
                t.setDescripcion(rs.getString("descripcion"));
            }

            rs.close();
            ps.close();
            conn.close();

        } catch (Exception e) {
            System.out.println("Error al obtener tarifa vigente: " + e.getMessage());
        }

        return t;
    }
}
```

---

## Conclusión

Este sistema demuestra una implementación **profesional y educativa** de:

✅ **JDBC puro** - Sin frameworks ORM como Hibernate  
✅ **Procedimientos Almacenados** - Lógica centralizada en BD  
✅ **CallableStatement** - Ejecución segura y eficiente  
✅ **Arquitectura MVC** - Bien estructurada y modular  
✅ **Interfaz gráfica** - Moderna, profesional con Swing  
✅ **Manejo robusto** - De excepciones y validaciones  
✅ **Documentación** - Completa y clara  

El código es **modular, mantenible y escalable** para futuras mejoras y puede servir como referencia para proyectos similares.

---

## Licencia y Uso

Este proyecto fue desarrollado con fines educativos para demostrar buenas prácticas en:
- Desarrollo Java
- Uso de JDBC
- Diseño de Procedimientos Almacenados
- Arquitectura MVC

**Abierto para uso educativo y referencia profesional.**

---

**Última actualización**: Mayo 17, 2026
'