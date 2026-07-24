package com.vasav.springmodulithlibrarymanagement.user.api;

import com.vasav.springmodulithlibrarymanagement.address.api.AddressRequest;

public record UserRequest(
        String username,
        String email,
        String password,
        String firstName,
        String lastName,
        String phone,
        AddressRequest address,
        Long preferredBranchId,
        UserRole userRole,
        boolean active
) {
}