package com.vasav.springmodulithlibrarymanagement.address.api;

import java.time.LocalDateTime;

public record AddressResponse(
        Long id,
        String street,
        String city,
        String stateProvince,
        String postalCode,
        String country,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
