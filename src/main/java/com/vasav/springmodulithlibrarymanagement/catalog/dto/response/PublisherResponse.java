package com.vasav.springmodulithlibrarymanagement.catalog.dto.response;

import java.time.Instant;

public record PublisherResponse(
        Long id,
        String name,
        AddressResponse address,
        String phone,
        String email,
        Instant createdAt,
        Instant updatedAt
) {
}