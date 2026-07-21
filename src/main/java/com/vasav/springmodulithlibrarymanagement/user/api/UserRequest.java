package com.vasav.springmodulithlibrarymanagement.user.api;

import com.vasav.springmodulithlibrarymanagement.user.internal.entity.UserRole;

public record UserRequest(
        String username,
        String email,
        String password,
        String firstName,
        String lastName,
        String phone,
        Long addressId,
        Long preferredBranchId,
        UserRole userRole,
        boolean active
) {
}