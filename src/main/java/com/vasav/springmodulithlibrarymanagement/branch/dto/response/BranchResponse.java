package com.vasav.springmodulithlibrarymanagement.branch.dto.response;

import java.time.Instant;

public record BranchResponse(
        Long id,
        String name,
        AddressResponse address,
        String phone,
        String email,
        String openingHours,
        Boolean isActive,
        Instant createdAt,
        Instant updatedAt
) {
}