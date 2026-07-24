package com.vasav.springmodulithlibrarymanagement.user.api;

public record UserRegistrationRequest(
        String username,
        String email,
        String passwordHash,
        String firstName,
        String lastName,
        String phone,
        Long addressId,
        Long preferredBranchId
) {
}