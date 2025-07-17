package com.wellcare.accounts.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

/**
 * Utility class to handle JWT (JSON Web Token) operations like:
 * - Generating a token after login
 * - Extracting the username from the token
 * - Validating if the token is still valid and correctly signed
 */
@Component
public class JwtUtil {

    // A secret key used to sign and verify JWT tokens
    private final Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256); // Safer than a raw string

    // Token expiration time (10 hours)
    private final long expirationMs = 1000 * 60 * 60 * 10;

    /**
     * Generates a JWT token for the provided username.
     *
     * @param username the username for whom the token is generated
     * @return the JWT token string
     */
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username) // Who is this token for?
                .setIssuedAt(new Date()) // When was the token created?
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs)) // When will it expire?
                .signWith(secretKey) // Sign it using our secret key
                .compact(); // Build the final string token
    }

    /**
     * Extracts the username (subject) from the token.
     *
     * @param token the JWT token
     * @return the username
     */
    public String extractUsername(String token) {
        return parseToken(token).getBody().getSubject();
    }

    /**
     * Validates the JWT token.
     * It checks:
     * - If it's correctly signed
     * - If it's not expired
     *
     * @param token the JWT token
     * @return true if valid, false otherwise
     */
    public boolean validateToken(String token) {
        try {
            parseToken(token); // Just try to parse it. If it fails, it's not valid.
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    // Helper method to parse token and return the parsed result
    private Jws<Claims> parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey) // Use our same signing key
                .build()
                .parseClaimsJws(token); // Parse the token and return claims
    }
}
