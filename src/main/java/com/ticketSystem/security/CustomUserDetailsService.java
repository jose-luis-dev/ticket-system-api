package com.ticketSystem.security;

import com.ticketSystem.enums.EstadoRegistro;
import com.ticketSystem.model.Usuario;
import com.ticketSystem.repository.IUsuarioRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final IUsuarioRepository usuarioRepository;

    // se inyecta la interfaz, no la implementación concreta (SOLID)
    public CustomUserDetailsService(IUsuarioRepository usuarioRepository){
        this.usuarioRepository = usuarioRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // Busca en DB
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Usuario no encontrado: " + username
                ));

        // Verifica que esté ACTIVO - Usuario INACTIVO no puede autenticarse
        if (usuario.getEstado() == EstadoRegistro.INACTIVO) {
            throw new UsernameNotFoundException(
                    "Usuario inactivo: " + username
            );
        }


        return User.builder()
                .username(usuario.getUsername())
                .password(usuario.getPassword())
                .authorities(List.of(
                        new SimpleGrantedAuthority("ROLE_" + usuario.getRol().name())
                ))
                .build();
    }

}
