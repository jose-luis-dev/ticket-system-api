CREATE TABLE usuarios (
    id          BIGINT          AUTO_INCREMENT PRIMARY KEY,
    username    VARCHAR(50)     NOT NULL UNIQUE,
    password    VARCHAR(255)    NOT NULL,
    nombre      VARCHAR(100)    NOT NULL,
    email       VARCHAR(100)    NOT NULL UNIQUE,
    rol         ENUM('ADMIN','USER') NOT NULL DEFAULT 'USER',
    estado      ENUM('ACTIVO','INACTIVO') NOT NULL DEFAULT 'ACTIVO',
    created_at  DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by  VARCHAR(50)     NULL
);