package com.ticketSystem.application;

import com.ticketSystem.service.TicketService;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class TicketStatisticsUseCase {
    private final TicketService ticketService;

    public TicketStatisticsUseCase(TicketService ticketService){
        this.ticketService = ticketService;
    }

    public Map<String, Integer> execute(){
        return ticketService.obtenerEstadisticas();
    }
}
