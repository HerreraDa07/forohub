package com.forohub.forohub.seguridad.autenticacion;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.forohub.forohub.dominio.usuario.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class ServicioToken {
    @Value("${JWT_SECRET}")
    private String api;

    public String obtenerToken(Usuario usuario) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(api);
            return JWT.create().withIssuer("ForoHub").withSubject(usuario.getCorreo()).withClaim("id", usuario.getId()).withExpiresAt(expiracion()).sign(algorithm);
        } catch (JWTCreationException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public String obtenerUsuario(String token) {
        if (token == null) {
            throw new RuntimeException();
        }
        DecodedJWT decodedJWT = null;
        try {
            Algorithm algorithm = Algorithm.HMAC256(api);
            decodedJWT = JWT.require(algorithm).withIssuer("ForoHub").build().verify(token);
            decodedJWT.getSubject();
        } catch (JWTVerificationException e) {
            throw new RuntimeException(e.getMessage());
        }
        if (decodedJWT.getSubject() == null) {
            throw new RuntimeException("El token ingresado no es válido");
        }
        return decodedJWT.getSubject();
    }

    public String obtenerCorreo(String token) {
        Algorithm algorithm = Algorithm.HMAC256(api);
        JWTVerifier jwtVerifier = JWT.require(algorithm).withIssuer("ForoHub").build();
        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        return decodedJWT.getClaim("sub").asString();
    }

    private Instant expiracion() {
        return LocalDateTime.now().plusHours(3).toInstant(ZoneOffset.of("-05:00"));
    }
}