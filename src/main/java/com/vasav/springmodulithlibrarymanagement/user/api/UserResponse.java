package com.vasav.springmodulithlibrarymanagement.user.api;

import com.vasav.springmodulithlibrarymanagement.user.internal.entity.UserRole;

import java.time.LocalDateTime;

public record UserResponse(
        Long id,
        String username,
        String email,
        String firstName,
        String lastName,
        String phone,
        Long addressId,
        Long preferredBranchId,
        UserRole userRole,
        boolean active,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}