package com.wellcare.accounts.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class JwtUtil {

    private final Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private final long expirationMs = 1000 * 60 * 60 * 10; // 10 hours

    // ✅ Generate JWT with username and roles
    public String generateToken(String email, Set<String> roles) {

        return Jwts.builder()
                .claim("roles",roles)

                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(secretKey)
                .compact();
    }

    // ✅ Extract username
    public String extractUsername(String token) {
        return parseToken(token).getBody().getSubject();
    }

    // ✅ Extract roles from token
    public List<String> extractRoles(String token) {
        Claims claims = parseToken(token).getBody();
        Object roles = claims.get("roles");

        if (roles instanceof List<?>) {
            return ((List<?>) roles).stream()
                    .map(Object::toString)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    // ✅ Validate token
    public boolean validateToken(String token) {
        try {
            parseToken(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    private Jws<Claims> parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token);
    }
}
