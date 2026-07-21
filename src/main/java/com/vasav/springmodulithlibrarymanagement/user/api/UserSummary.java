package com.vasav.springmodulithlibrarymanagement.user.api;

public record UserSummary(
        Long id,
        String username,
        String firstName,
        String lastName,
        String email
) {}