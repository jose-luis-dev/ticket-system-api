package com.ticketSystem.model;

import com.ticketSystem.enums.EstadoOperacional;
import com.ticketSystem.enums.EstadoRegistro;
import com.ticketSystem.enums.Prioridad;

import java.util.Objects;

public class Ticket {
    private Integer idTicket;
    protected String titulo;
    protected String descripcion;
    private EstadoOperacional estadoOperacionalActual;
    private Prioridad prioridadActual;
    private EstadoRegistro estadoOperacionalTicket;

    public Ticket(String titulo, String descripcion){
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.estadoOperacionalActual = EstadoOperacional.ABIERTO;
        this.prioridadActual = Prioridad.ALTA;
        this.estadoOperacionalTicket = EstadoRegistro.ACTIVO;
    }


    public Ticket(Integer idTicket, String titulo, String descripcion){
        this.idTicket = idTicket;
        this.titulo = titulo;
        this.descripcion = descripcion;
    }


    public int getIdTicket() { return this.idTicket; }
    public void setIdTicket(Integer idTicket) {
        this.idTicket = idTicket;
    }

    public String getTitulo() { return this.titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo;}

    public String getDescripcion() { return this.descripcion; }
    public void setDescripcion(String descripcion) {this.descripcion = descripcion;}

    public Prioridad getPrioridadActual() { return prioridadActual; }
    public void setPrioridadActual(Prioridad prioridadActual) { this.prioridadActual = prioridadActual;}

    public EstadoOperacional getEstadoOperacionalActual() { return this.estadoOperacionalActual; }
    public void setEstadoOperacionalActual(EstadoOperacional estadoOperacionalActual) { this.estadoOperacionalActual = estadoOperacionalActual; }

    public EstadoRegistro getEstadoOperacionalTicket() { return this.estadoOperacionalTicket; }
    public void setEstadoOperacionalTicket(EstadoRegistro estadoOperacionalTicket) { this.estadoOperacionalTicket = estadoOperacionalTicket;}



    @Override
    public String toString() {
        return "Ticket{" +
                "idTicket=" + idTicket +
                ", titulo='" + titulo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", estadoActual=" + estadoOperacionalActual +
                ", prioridadActual=" + prioridadActual +
                ", estadoTicket=" + estadoOperacionalTicket +
                '}';
    }

    public void mostrarDetalle(){
        System.out.printf("""
                -- Número del ticket : %d
                \tTitulo: %s
                \tDescripcion: %s
                \tEstado Actual: %s
                \tPrioridad: %s
                \tEstado Ticket: %s
                -----------------------------
                """, getIdTicket(),getTitulo()
                ,getDescripcion(),getEstadoOperacionalActual(),getPrioridadActual(),getEstadoOperacionalTicket());
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return Objects.equals(idTicket, ticket.idTicket) && Objects.equals(titulo, ticket.titulo) && Objects.equals(descripcion, ticket.descripcion) && estadoOperacionalActual == ticket.estadoOperacionalActual && prioridadActual == ticket.prioridadActual && estadoOperacionalTicket == ticket.estadoOperacionalTicket;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTicket, titulo, descripcion, estadoOperacionalActual, prioridadActual, estadoOperacionalTicket);
    }
}
