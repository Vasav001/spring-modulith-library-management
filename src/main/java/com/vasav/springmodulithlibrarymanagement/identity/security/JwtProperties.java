package com.vasav.springmodulithlibrarymanagement.identity.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.jwt")
public record JwtProperties(
        String secret,
        long accessTokenExpiration,
        long refreshTokenExpiration,
        long emailVerificationTokenExpiration
) {
}
