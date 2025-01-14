package com.tfg.GoAway.modules.shared.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private static final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256); // Clave segura generada automáticamente
    private static final long EXPIRATION_TIME = 86400000; // 1 día en milisegundos

    // Genera el token JWT
    public String generateToken(String email, String name, String role) {
        return Jwts.builder()
            .setSubject(email)
            .claim("name", name)
            .claim("role", role)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
            .signWith(SECRET_KEY) // Firma con la clave secreta
            .compact();
    }

    // Valida el token JWT
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY) // Clave para verificar la firma
                .build()
                .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Extrae el email del token JWT
    public String getEmailFromToken(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(SECRET_KEY) // Clave para verificar la firma
            .build()
            .parseClaimsJws(token)
            .getBody()
            .getSubject();
    }

    // Extrae el rol del token JWT
    public String getRoleFromToken(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(SECRET_KEY) // Clave para verificar la firma
            .build()
            .parseClaimsJws(token)
            .getBody()
            .get("role", String.class);
    }
}
