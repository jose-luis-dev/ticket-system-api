package com.ticketSystem.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;





import java.io.IOException;
import java.util.List;

@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter (JwtService jwtService,
                                    UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
        throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        // Si no hay token -> continuar
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String jwt = authHeader.substring(7);

        try {
            final String username = jwtService.extractUsername(jwt);
            // Validar si no esta autenticado aun
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                log.debug("Token válido para el usuario: {}", username); // Nivel 3 (Debug)

                if (jwtService.isTokenValid(jwt, userDetails.getUsername())) {
                    // Extraemos el rol del token
                    String roleFromToken = jwtService.extractRole(jwt);

                    List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(roleFromToken));
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    authorities
                            );

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        } catch (ExpiredJwtException e){
            log.warn("Intento de acceso con token expirado: {}", e.getMessage()); // Nivel 2 (Warn)
            enviarErrorJson(response, "Token expirado", HttpServletResponse.SC_UNAUTHORIZED);
            return;
        } catch (MalformedJwtException | SignatureException e){
            log.error("Token malformado o firma inválida: {}", e.getMessage()); // Nivel 1 (Error)
            enviarErrorJson(response, "Token inválido", HttpServletResponse.SC_UNAUTHORIZED);
            return;
        } catch (Exception e){
            log.error("Fallo inesperado en la autenticación JWT: {}", e);
            enviarErrorJson(response, "Error de autenticación", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }
        filterChain.doFilter(request, response);
    }

    private void enviarErrorJson(HttpServletResponse response, String mensaje, int status) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json");
        response.getWriter().write(String.format("{\"error\": \"%s\"}", mensaje));

    }

}
