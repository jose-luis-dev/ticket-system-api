CREATE TABLE asignaciones (
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    ticket_id       BIGINT       NOT NULL,
    agente_id       BIGINT       NOT NULL,
    asignado_por    BIGINT       NULL,
    tipo            VARCHAR(30)  NOT NULL,
    motivo          VARCHAR(255) NULL,
    comentario      TEXT         NULL,
    fecha_asignacion DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_asignacion_ticket
        FOREIGN KEY (ticket_id)  REFERENCES tickets(id),

    CONSTRAINT fk_asignacion_agente
        FOREIGN KEY (agente_id)  REFERENCES usuarios(id),

    CONSTRAINT fk_asignacion_por
        FOREIGN KEY (asignado_por) REFERENCES usuarios(id)
);