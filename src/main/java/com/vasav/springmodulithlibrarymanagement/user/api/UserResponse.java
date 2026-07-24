package com.vasav.springmodulithlibrarymanagement.user.api;

import com.vasav.springmodulithlibrarymanagement.address.api.AddressResponse;

import java.time.LocalDateTime;

public record UserResponse(
        Long id,
        String username,
        String email,
        String firstName,
        String lastName,
        String phone,
        AddressResponse address,
        Long preferredBranchId,
        UserRole userRole,
        boolean active,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}