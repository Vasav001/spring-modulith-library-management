package com.vasav.springmodulithlibrarymanagement.catalog.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PublisherCreateRequest(
        @NotBlank @Size(max = 200) String name,
        @Valid AddressRequest address,
        @Size(max = 20) String phone,
        @Email @Size(max = 100) String email
) {
}