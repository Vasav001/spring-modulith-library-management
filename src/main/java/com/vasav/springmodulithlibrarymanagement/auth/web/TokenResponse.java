package com.vasav.springmodulithlibrarymanagement.auth.web;

public record TokenResponse(
        String accessToken,
        String tokenType,
        long expiresIn
) {
}