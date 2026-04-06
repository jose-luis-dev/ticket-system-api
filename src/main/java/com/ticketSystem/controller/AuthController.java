package com.ticketSystem.controller;

import com.ticketSystem.controller.dto.LoginRequest;
import com.ticketSystem.controller.dto.LoginResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request){

        if ("admin".equals(request.getUsername()) && "1234".equals(request.getPassword())){
            return ResponseEntity.ok(new LoginResponse("admin", "ADMIN"));
        }
        if ("user".equals(request.getUsername()) && "1234".equals(request.getPassword())){
            return ResponseEntity.ok(new LoginResponse("user", "USER"));
        }

        return ResponseEntity.status(401).build();
    }

}
