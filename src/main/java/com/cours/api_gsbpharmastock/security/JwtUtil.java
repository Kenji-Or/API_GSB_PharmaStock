package com.cours.api_gsbpharmastock.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

    @Value("${jwt.secret}") // Injecte la clé secrète depuis application.properties
    private String secretKey;

    // Convertit la clé secrète en Key
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(java.util.Base64.getDecoder().decode(secretKey));
    }

    public String generateToken(String mail, String idUser, String idRole) {
        // Créer un objet Claims (les données à stocker dans le token)
        Map<String, Object> claims = new HashMap<>();
        claims.put("idUser", idUser);
        claims.put("idRole", idRole);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(mail)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // Expire dans 1h
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token, String mail) {
        return (mail.equals(extractEmail(token)) && !isTokenExpired(token));
    }

    // Method to validate the token
    public boolean isTokenValid(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return !claims.getExpiration().before(new java.util.Date());
        } catch (Exception e) {
            return false;
        }
    }

    public Long extractUserId(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        Object idUserObj = claims.get("idUser"); // Récupération brute

        if (idUserObj instanceof Integer) {
            return ((Integer) idUserObj).longValue(); // Convertit Integer en Long
        } else if (idUserObj instanceof String) {
            return Long.parseLong((String) idUserObj); // Convertit String en Long
        } else {
            throw new IllegalArgumentException("Le claim 'idUser' n'est pas valide");
        }
    }



    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claimsResolver.apply(claims);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
}
