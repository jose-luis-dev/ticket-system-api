-- V1__create_tickets_table.sql
CREATE TABLE tickets (
    id                  INT             AUTO_INCREMENT PRIMARY KEY,
    titulo              VARCHAR(100)    NOT NULL,
    descripcion         TEXT,
    estado_operacional  ENUM('ABIERTO','EN_PROCESO','CERRADO','CANCELADO')
                        NOT NULL DEFAULT 'ABIERTO',
    prioridad           ENUM('ALTA','MEDIA','BAJA')
                        NOT NULL DEFAULT 'MEDIA',
    estado_registro     ENUM('ACTIVO','INACTIVO')
                        NOT NULL DEFAULT 'ACTIVO',
    fecha_creacion      TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP
);