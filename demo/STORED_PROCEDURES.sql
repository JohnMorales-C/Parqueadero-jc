-- ==========================================
-- SISTEMA DE PARQUEADERO JC
-- Base de datos: parqueadero_jc
-- Script de Procedimientos Almacenados
-- ==========================================

USE parqueadero_jc;

-- ==========================================
-- PROCEDIMIENTOS ALMACENADOS - CLIENTE
-- ==========================================

-- Objetivo: Registrar un nuevo cliente en el sistema.
-- Parámetros:
--   p_nombre → nombre del cliente
--   p_documento → documento único
--   p_telefono → teléfono
--   p_correo → correo electrónico
-- Funcionamiento: Inserta un nuevo registro en la tabla cliente.

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

-- Objetivo: Listar todos los clientes registrados.
-- Parámetros: Ninguno
-- Funcionamiento: Selecciona todos los registros de la tabla cliente.

DELIMITER $$

CREATE PROCEDURE sp_listar_clientes()
BEGIN
    SELECT * FROM cliente;
END $$

DELIMITER ;

-- Objetivo: Actualizar la información de un cliente existente.
-- Parámetros:
--   p_id → ID del cliente a actualizar
--   p_nombre → nuevo nombre
--   p_documento → nuevo documento
--   p_telefono → nuevo teléfono
--   p_correo → nuevo correo
-- Funcionamiento: Actualiza un cliente por su ID.

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

-- Objetivo: Eliminar un cliente del sistema.
-- Parámetros:
--   p_id → ID del cliente a eliminar
-- Funcionamiento: Elimina un cliente por su ID.

DELIMITER $$

CREATE PROCEDURE sp_eliminar_cliente(
    IN p_id INT
)
BEGIN
    DELETE FROM cliente
    WHERE id_cliente = p_id;
END $$

DELIMITER ;

-- ==========================================
-- PROCEDIMIENTOS ALMACENADOS - VEHÍCULO
-- ==========================================

-- Objetivo: Registrar un nuevo vehículo.
-- Parámetros:
--   p_placa → placa del vehículo
--   p_id_cliente → ID del cliente propietario
--   p_id_tipo → ID del tipo de vehículo
--   p_id_marca → ID de la marca
--   p_id_color → ID del color
--   p_id_estado → ID del estado del vehículo
-- Funcionamiento: Inserta un nuevo vehículo en la tabla vehiculo.

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

-- Objetivo: Listar todos los vehículos con información relacionada.
-- Parámetros: Ninguno
-- Funcionamiento: Selecciona todos los vehículos con JOIN a las tablas relacionadas.

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

-- ==========================================
-- PROCEDIMIENTOS ALMACENADOS - INGRESO
-- ==========================================

-- Objetivo: Registrar el ingreso de un vehículo al parqueadero.
-- Parámetros:
--   p_id_vehiculo → ID del vehículo
--   p_id_espacio → ID del espacio asignado
--   p_id_usuario → ID del usuario que registra
--   p_fecha → fecha y hora de entrada
-- Funcionamiento: Inserta un nuevo registro en la tabla ingreso.

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

-- Objetivo: Registrar la salida de un vehículo del parqueadero.
-- Parámetros:
--   p_id_ingreso → ID del ingreso a actualizar
--   p_fecha_salida → fecha y hora de salida
--   p_total → total a pagar calculado
-- Funcionamiento: Actualiza el registro de ingreso con la salida y total.

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

-- ==========================================
-- PROCEDIMIENTOS ALMACENADOS - TARIFA
-- ==========================================

-- Objetivo: Insertar una nueva tarifa en el sistema.
-- Parámetros:
--   p_codigo → código único de la tarifa (ej: 2026-001)
--   p_anio → año de vigencia
--   p_id_tipo → ID del tipo de vehículo
--   p_tipo_cobro → tipo de cobro (HORA, DIA, MES)
--   p_horas_base → horas incluidas en el valor base
--   p_valor_base → valor base a pagar
--   p_valor_adicional → valor por hora adicional
--   p_estado → estado de la tarifa (VIGENTE, CADUCADA)
--   p_descripcion → descripción de la tarifa
-- Funcionamiento: Inserta una nueva tarifa en la tabla tarifa.

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
