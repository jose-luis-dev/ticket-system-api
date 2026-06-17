ALTER TABLE tickets
    ADD COLUMN agente_id BIGINT NULL,
    ADD CONSTRAINT fk_ticket_agente
        FOREIGN KEY (agente_id) REFERENCES usuarios(id);