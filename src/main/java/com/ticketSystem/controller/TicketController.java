package com.ticketSystem.controller;

import com.ticketSystem.application.CreateTicketUseCase;
import com.ticketSystem.application.DeleteTicketUseCase;
import com.ticketSystem.application.GetTicketByUseCase;
import com.ticketSystem.application.ListTicketUseCase;
import com.ticketSystem.controller.dto.ApiResponse;
import com.ticketSystem.controller.dto.CreateTicketRequest;
import com.ticketSystem.controller.dto.TicketMapper;
import com.ticketSystem.controller.dto.TicketResponse;
import com.ticketSystem.enums.RolUsuario;
import com.ticketSystem.model.Ticket;
import com.ticketSystem.model.Usuario;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    private final CreateTicketUseCase createTicketUseCase;
    private final GetTicketByUseCase getTicketByUseCase;
    private final ListTicketUseCase listTicketUseCase;
    private final DeleteTicketUseCase deleteTicketUseCase;

    public TicketController(CreateTicketUseCase createTicketUseCase,
                            GetTicketByUseCase getTicketByUseCase,
                            ListTicketUseCase listTicketUseCase,
                            DeleteTicketUseCase deleteTicketUseCase) {
        this.createTicketUseCase = createTicketUseCase;
        this.getTicketByUseCase = getTicketByUseCase;
        this.listTicketUseCase = listTicketUseCase;
        this.deleteTicketUseCase = deleteTicketUseCase;
    }


    @GetMapping
    public ResponseEntity<ApiResponse<List<TicketResponse>>> listarTicket(){

        List<TicketResponse> responseList = listTicketUseCase.execute()
                .stream()
                .map(TicketMapper::toResponse)
                .toList();
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Lista de tickets",
                responseList
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse <TicketResponse>> obtenerTicket(@PathVariable int id){
        TicketResponse response = TicketMapper.toResponse (getTicketByUseCase.execute(id));

        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Ticket obtenido",
                response
        ));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<TicketResponse>> crearTicket(@Valid @RequestBody CreateTicketRequest request){

        Ticket ticket = createTicketUseCase.execute(
                request.getTitulo(),
                request.getDescripcion()
        );
        TicketResponse response = TicketMapper.toResponse(ticket);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiResponse<>(
                        true,
                        "Ticket creado correctamente",
                        response
                ));
    }

    private RolUsuario parseRol(String role){
        try {
            return RolUsuario.valueOf(role);
        }catch (IllegalArgumentException e){
            throw new IllegalArgumentException("Rol inválido: " + role);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTicket(@PathVariable int id, @RequestHeader("X-User-Role") String role){

        RolUsuario rolUsuario = parseRol(role);
        Usuario usuarioActual = new Usuario(rolUsuario);
        deleteTicketUseCase.execute(id, usuarioActual);
        return ResponseEntity.noContent().build();
    }

}
