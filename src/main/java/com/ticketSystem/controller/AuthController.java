package com.ticketSystem.controller;

import com.ticketSystem.application.RegisterUserUseCase;
import com.ticketSystem.controller.dto.*;
import com.ticketSystem.model.Usuario;
import com.ticketSystem.security.JwtService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RegisterUserUseCase registerUserUseCase;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtService jwtService, RegisterUserUseCase registerUserUseCase) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.registerUserUseCase = registerUserUseCase;
    }


    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );

            String username = authentication.getName();
            String role = authentication.getAuthorities().iterator().next().getAuthority();
            // Genera Token
            String token = jwtService.generateToken(username, role);

            return ResponseEntity.ok(new AuthResponse(token));

        } catch (org.springframework.security.core.AuthenticationException ex) {
            return ResponseEntity.status(401).build();
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UsuarioResponse>> register(
            @Valid @RequestBody RegisterUserRequest request,
            Authentication authentication) {

            // Quién está creando este usuario (auditoría)
            String createdBy = authentication.getName();

            Usuario nuevo = registerUserUseCase.execute(request, createdBy);
            UsuarioResponse response = UsuarioMapper.toResponse(nuevo);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(ApiResponse.success(response, "Usuario creado exitosamente"));

    }

}
