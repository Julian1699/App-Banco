package com.server.api.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // 1. Validar que sea un Header Authorization valido
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    
        if (authHeader == null || authHeader.isEmpty() || !authHeader.startsWith("Bearer")) {
            filterChain.doFilter(request, response);
            return;
        }
    
        // 2. Validar que el JWT sea valido
        String jwt = authHeader.split(" ")[1].trim();
    
        if (!this.jwtUtil.isValid(jwt)) {
            filterChain.doFilter(request, response);
            return;
        }
    
        // 3. Cargar la información necesaria del token JWT
        String correo = this.jwtUtil.getUsername(jwt);
        String userId = this.jwtUtil.getUserIdFromToken(jwt);
    
        // 4. Crear un objeto de autenticación con la información extraída del JWT
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                correo, null, null  // No necesitamos el password ni authorities aquí
        );
    
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    
        filterChain.doFilter(request, response);
    }    
}
