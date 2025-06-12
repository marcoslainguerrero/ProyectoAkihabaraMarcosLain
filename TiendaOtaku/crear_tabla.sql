-- Elimina la base de datos AkihabaraDB si ya existe para evitar conflictos
DROP DATABASE IF EXISTS AkihabaraDB;

-- Crea una nueva base de datos llamada AkihabaraDB
CREATE DATABASE AkihabaraDB;

-- Selecciona la base de datos AkihabaraDB para usarla en las siguientes operaciones
USE AkihabaraDB;

-- Crea la tabla productos si no existe, con las columnas id, nombre, categoria, precio y stock
CREATE TABLE IF NOT EXISTS productos (
    id INT PRIMARY KEY AUTO_INCREMENT,      -- Identificador único autoincremental
    nombre VARCHAR(255) NOT NULL,            -- Nombre del producto (no puede ser nulo)
    categoria VARCHAR(100),                  -- Categoría del producto
    precio DECIMAL(10,2),                    -- Precio con 2 decimales
    stock INT                               -- Cantidad en inventario
);

-- Crea el usuario AkihabaraUser con contraseña TiendaOtaku23 si no existe ya
CREATE USER IF NOT EXISTS 'AkihabaraUser'@'localhost' IDENTIFIED BY 'TiendaOtaku23';

-- Otorga permisos de SELECT, INSERT, UPDATE y DELETE sobre la base de datos AkihabaraDB al usuario AkihabaraUser
GRANT SELECT, INSERT, UPDATE, DELETE ON AkihabaraDB.* TO 'AkihabaraUser'@'localhost';

-- Aplica los cambios de permisos inmediatamente
FLUSH PRIVILEGES;
