package com.vasav.springmodulithlibrarymanagement.identity.security;

import com.vasav.springmodulithlibrarymanagement.identity.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtService {

    private static final String CLAIM_EMAIL = "email";
    private static final String CLAIM_ROLE = "role";
    private static final String CLAIM_PURPOSE = "purpose";
    private static final String PURPOSE_ACCESS = "ACCESS";
    private static final String PURPOSE_REFRESH = "REFRESH";
    private static final String PURPOSE_EMAIL_VERIFICATION = "EMAIL_VERIFICATION";
    private final JwtProperties jwtProperties;

    public String generateAccessToken(User user) {
        return buildToken(user, jwtProperties.accessTokenExpiration(), PURPOSE_ACCESS);
    }

    public String generateRefreshToken(User user) {
        return buildToken(user, jwtProperties.refreshTokenExpiration(), PURPOSE_REFRESH);
    }

    public String generateEmailVerificationToken(User user) {
        return buildToken(user, jwtProperties.emailVerificationTokenExpiration(), PURPOSE_EMAIL_VERIFICATION);
    }

    private String buildToken(User user, long expirationMillis, String purpose) {
        Instant now = Instant.now();

        return Jwts.builder()
                .subject(user.getId().toString())
                .claim(CLAIM_EMAIL, user.getEmail())
                .claim(CLAIM_ROLE, user.getUserRole().name())
                .claim(CLAIM_PURPOSE, purpose)
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusMillis(expirationMillis)))
                .signWith(getSigningKey())
                .compact();
    }

    public Long extractUserId(String token) {
        return Long.valueOf(extractAllClaims(token).getSubject());
    }

    public String extractPurpose(String token) {
        return extractAllClaims(token).get(CLAIM_PURPOSE, String.class);
    }

    public boolean isTokenValid(String token) {
        try {
            extractAllClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public boolean isAccessToken(String token) {
        return PURPOSE_ACCESS.equals(extractPurpose(token));
    }

    public boolean isRefreshToken(String token) {
        return PURPOSE_REFRESH.equals(extractPurpose(token));
    }

    public boolean isEmailVerificationToken(String token) {
        return PURPOSE_EMAIL_VERIFICATION.equals(extractPurpose(token));
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtProperties.secret());
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
