package com.ticketSystem.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // Simulador luego irá BD
        if ("admin".equals(username)){
            return User.builder()
                    .username("admin")
                    .password("{noop}1234")  // {noop} = sin encriptar - temporal
                    .roles("ADMIN")
                    .build();
        }

        if ("user".equals(username)){
            return User.builder()
                    .username("user")
                    .password("{noop}1234")
                    .roles("USER")
                    .build();
        }

        throw new UsernameNotFoundException("User not found");
    }

}
