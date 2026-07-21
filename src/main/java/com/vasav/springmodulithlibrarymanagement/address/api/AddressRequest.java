package com.vasav.springmodulithlibrarymanagement.address.api;

public record AddressRequest(
        String street,
        String city,
        String stateProvince,
        String postalCode,
        String country
) {
}