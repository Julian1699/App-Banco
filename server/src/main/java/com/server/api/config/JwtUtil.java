package com.server.api.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class JwtUtil {

    private static final String SECRET_KEY = "secret_key"; 
    private static final Algorithm ALGORITHM = Algorithm.HMAC256(SECRET_KEY);

    public String create(Long userId, String correo, String nombres, Boolean habilitado) {
        return JWT.create()
                .withSubject(userId.toString())
                .withClaim("correo", correo)
                .withClaim("nombre", nombres)
                .withClaim("habilitado", habilitado)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(1)))
                .sign(ALGORITHM);
    }

    public boolean isValid(String jwt) {
        try {
            JWT.require(ALGORITHM).build().verify(jwt);
            return true;
        } catch (JWTVerificationException e) {
            return false;
        }
    }

    public String getUsername(String jwt) {
        return JWT.require(ALGORITHM).build().verify(jwt).getSubject();
    }

    public String getUserIdFromToken(String jwt) {
        return JWT.require(ALGORITHM).build().verify(jwt).getClaim("userId").asString();
    }

    public Boolean getUserHabilitadoFromToken(String jwt) {
        return JWT.require(ALGORITHM).build().verify(jwt).getClaim("habilitado").asBoolean();
    }
}