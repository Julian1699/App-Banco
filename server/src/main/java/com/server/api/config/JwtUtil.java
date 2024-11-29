package com.server.api.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.server.api.service.RolPermisoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Utilidad para crear, validar y extraer información de tokens JWT.
 */
@Component
public class JwtUtil {

    private static final String SECRET_KEY = "secret_key";

    private static final Algorithm ALGORITHM = Algorithm.HMAC256(SECRET_KEY);

    @Autowired
    private RolPermisoService rolPermisoService;
    /**
     * Crea un token JWT con la información proporcionada.
     * 
     * @param userId     El ID del usuario
     * @param correo     El correo del usuario
     * @param nombres    Los nombres del usuario
     * @param habilitado Estado de habilitación del usuario
     * @param roles      Lista de roles del usuario
     * @param permisos   Lista de IDs de permisos del usuario
     * @return El token JWT generado
     */
    public String create(Long userId, String correo, String nombres, Boolean habilitado, List<String> roles, List<Long> permisos) {
        return JWT.create()
                .withSubject(userId.toString())
                .withClaim("correo", correo)
                .withClaim("nombre", nombres)
                .withClaim("habilitado", habilitado)
                .withClaim("roles", roles)
                .withClaim("permisos", permisos) // Guardar IDs de permisos en lugar de nombres
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(1)))
                .sign(ALGORITHM);
    }

    // Método para extraer permisos del token
    public List<Long> getUserPermissionsFromToken(String jwt) {
        return JWT.require(ALGORITHM).build().verify(jwt).getClaim("permisos").asList(Long.class);
    }

    // Método para extraer roles del token
    public List<String> getUserRolesFromToken(String jwt) {
        return JWT.require(ALGORITHM).build().verify(jwt).getClaim("roles").asList(String.class);
    }

    /**
     * Valida si el token JWT es válido.
     * 
     * @param jwt El token JWT
     * @return true si el token es válido, false en caso contrario
     */
    public boolean isValid(String jwt) {
        try {
            JWT.require(ALGORITHM).build().verify(jwt);
            return true;
        } catch (JWTVerificationException e) {
            return false;
        }
    }

    /**
     * Extrae el userId del token JWT.
     * 
     * @param jwt El token JWT
     * @return El userId del usuario
     */
    public String getUserIdFromToken(String jwt) {
        return JWT.require(ALGORITHM).build().verify(jwt).getSubject();
    }

    /**
     * Extrae el correo del token JWT.
     * 
     * @param jwt El token JWT
     * @return El correo del usuario
     */
    public String getUsername(String jwt) {
        return JWT.require(ALGORITHM).build().verify(jwt).getClaim("correo").asString();
    }

    /**
     * Extrae el estado de habilitación del token JWT.
     * 
     * @param jwt El token JWT
     * @return El estado de habilitación del usuario
     */
    public Boolean getUserHabilitadoFromToken(String jwt) {
        return JWT.require(ALGORITHM).build().verify(jwt).getClaim("habilitado").asBoolean();
    }
}
