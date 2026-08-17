package com.vasav.springmodulithlibrarymanagement.identity.dto.request;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public record AddressRequest(
        @NotBlank String street,
        @NotBlank String city,
        @NotBlank String stateProvince,
        @NotBlank String postalCode,
        String country,
        @DecimalMin("-90.0") @DecimalMax("90.0") BigDecimal latitude,
        @DecimalMin("-180.0") @DecimalMax("180.0") BigDecimal longitude
) {
}