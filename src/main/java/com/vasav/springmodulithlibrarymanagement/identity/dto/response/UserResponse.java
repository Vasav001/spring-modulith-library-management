package com.vasav.springmodulithlibrarymanagement.identity.dto.response;

import com.vasav.springmodulithlibrarymanagement.identity.entity.AccountStatus;
import com.vasav.springmodulithlibrarymanagement.identity.entity.UserRole;

import java.time.Instant;

public record UserResponse(
        Long id,
        String username,
        String email,
        String firstName,
        String lastName,
        String phone,
        boolean emailVerified,
        boolean addressVerified,
        AddressResponse address,
        Long preferredBranchId,
        UserRole role,
        AccountStatus accountStatus,
        Instant lastLoginAt,
        Instant createdAt
) {
}