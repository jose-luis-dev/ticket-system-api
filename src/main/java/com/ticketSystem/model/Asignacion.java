package com.ticketSystem.model;

import com.ticketSystem.enums.TipoAsignacion;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

public class Asignacion {

    private Long id;
    private Integer ticketId;
    private Long agenteId;
    private Long asignadoPor;  // Aqui se aplica NULL = asignado por el sistema default
    private TipoAsignacion tipo;
    private String motivo;
    private String comentario;
    private LocalDateTime fechaAsignacion;

    public Asignacion () {}

    public Asignacion(Integer ticketId,
                      Long agenteId,
                      Long asignadoPor,
                      TipoAsignacion tipo,
                      String motivo,
                      String comentario) {
        
        this.ticketId = ticketId;
        this.agenteId = agenteId;
        this.asignadoPor = asignadoPor;
        this.tipo = tipo;
        this.motivo = motivo;
        this.comentario = comentario;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTicketId() {
        return ticketId;
    }
    public void setTicketId(Integer ticketId) {
        this.ticketId = ticketId;
    }

    public Long getAgenteId() {
        return agenteId;
    }
    public void setAgenteId(Long agenteId) {
        this.agenteId = agenteId;
    }

    public Long getAsignadoPor() {
        return asignadoPor;
    }
    public void setAsignadoPor(Long asignadoPor) {
        this.asignadoPor = asignadoPor;
    }

    public TipoAsignacion getTipo() {
        return tipo;
    }
    public void setTipo(TipoAsignacion tipo) {
        this.tipo = tipo;
    }

    public String getMotivo() {
        return motivo;
    }
    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getComentario() {
        return comentario;
    }
    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public LocalDateTime getFechaAsignacion() {
        return fechaAsignacion;
    }
    public void setFechaAsignacion(LocalDateTime fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }
}
