package com.vasav.springmodulithlibrarymanagement.identity.dto.response;

import java.math.BigDecimal;

public record AddressResponse(
        Long id,
        String street,
        String city,
        String stateProvince,
        String postalCode,
        String country,
        BigDecimal latitude,
        BigDecimal longitude
) {
}