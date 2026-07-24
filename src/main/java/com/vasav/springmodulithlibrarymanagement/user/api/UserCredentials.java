package com.vasav.springmodulithlibrarymanagement.user.api;

public record UserCredentials(
        Long id,
        String username,
        String email,
        String passwordHash,
        UserRole userRole,
        Boolean active
) {
}