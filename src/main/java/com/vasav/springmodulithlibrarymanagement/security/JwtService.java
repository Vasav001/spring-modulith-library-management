package com.vasav.springmodulithlibrarymanagement.security;

import com.vasav.springmodulithlibrarymanagement.user.api.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;

@Component
public class JwtService {
    @Value("${app.jwt.secret}")
    private String secret;

    @Getter
    @Value("${app.jwt.access-token-expiration-ms}")
    private long expirationMs;

    private SecretKey key() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateAccessToken(Long userId, String username, UserRole role) {
        Instant now = Instant.now();
        return Jwts.builder().subject(username)
                .claim("userId", userId).claim("role", role.toString())
                .issuedAt(Date.from(now)).expiration(Date.from(now.plusMillis(expirationMs)))
                .signWith(key()).compact();
    }

    Claims parseClaim(String token) {
        return Jwts.parser().verifyWith(key()).build()
                .parseSignedClaims(token).getPayload();
    }

    boolean isValid(String token) {
        try {
            parseClaim(token);
            return true;
        } catch (JwtException | IllegalArgumentException exception) {
            return false;
        }
    }

}
