package com.server.api.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // Validar que sea un Header Authorization válido
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    
        if (authHeader == null || authHeader.isEmpty() || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
    
        String jwt = authHeader.substring(7).trim(); // Obtener el JWT

        // Validar el JWT
        if (!jwtUtil.isValid(jwt)) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extraer información del JWT
        String correo = jwtUtil.getUsername(jwt);
        List<String> permisos = jwtUtil.getUserPermissionsFromToken(jwt); // Obtener permisos del JWT

        // Convertir permisos en authorities para Spring Security
        List<SimpleGrantedAuthority> authorities = permisos.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        // Validar si el usuario tiene el permiso necesario para el endpoint solicitado
        String endpoint = request.getRequestURI();
        String metodoHttp = request.getMethod();

        if (!tienePermisoParaAcceder(permisos, endpoint, metodoHttp)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("No tiene permisos para acceder a este recurso");
            return;
        }

        // Autenticar al usuario con authorities (permisos)
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                correo, null, authorities);
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        // Continuar con la cadena de filtros
        filterChain.doFilter(request, response);
    }

    // Método que verifica si los permisos del usuario permiten el acceso al endpoint
    private boolean tienePermisoParaAcceder(List<String> permisos, String endpoint, String metodoHttp) {
        // Lógica básica para definir si un usuario tiene acceso a un recurso particular según el endpoint y permisos

        if (endpoint.startsWith("/api/v1/roles")) {
            return permisos.contains("ACCESO_MODULO_ROLES");
        }
        if (endpoint.startsWith("/api/v1/usuarios")) {
            return permisos.contains("ACCESO_MODULO_USUARIOS");
        }
        if (endpoint.startsWith("/api/v1/permisos")) {
            return permisos.contains("ACCESO_MODULO_PERMISOS_ROLES");
        }

        // Agrega aquí la lógica para otros endpoints según los permisos disponibles
        return true; // Por defecto permitir el acceso si no hay restricciones específicas
    }
}
