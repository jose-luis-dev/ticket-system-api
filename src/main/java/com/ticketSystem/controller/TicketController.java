package com.ticketSystem.controller;

import com.ticketSystem.application.CreateTicketUseCase;
import com.ticketSystem.application.GetTicketByUseCase;
import com.ticketSystem.application.ListTicketUseCase;
import com.ticketSystem.controller.dto.CreateTicketRequest;
import com.ticketSystem.controller.dto.TicketMapper;
import com.ticketSystem.controller.dto.TicketResponse;
import com.ticketSystem.model.Ticket;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    private final CreateTicketUseCase createTicketUseCase;
    private final GetTicketByUseCase getTicketByUseCase;
    private final ListTicketUseCase listTicketUseCase;

    public TicketController(CreateTicketUseCase createTicketUseCase,
                            GetTicketByUseCase getTicketByUseCase,
                            ListTicketUseCase listTicketUseCase) {
        this.createTicketUseCase = createTicketUseCase;
        this.getTicketByUseCase = getTicketByUseCase;
        this.listTicketUseCase = listTicketUseCase;
    }


    @GetMapping
    public List<TicketResponse> listarTicket(){
        return listTicketUseCase.execute()
                .stream()
                .map(TicketMapper::toResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public TicketResponse obtenerTicket(@PathVariable int id){
        return TicketMapper.toResponse (getTicketByUseCase.execute(id));
    }

    @PostMapping
    public TicketResponse crearTicket(@RequestBody CreateTicketRequest request){

        Ticket ticket = createTicketUseCase.execute(
                request.getTitulo(),
                request.getDescripcion()
        );
        return TicketMapper.toResponse(ticket);
    }





}
