package com.vasav.springmodulithlibrarymanagement.identity.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;

public record UpdateProfileRequest(
        @Size(max = 100) String firstName,
        @Size(max = 100) String lastName,
        @Size(max = 20) String phone,
        @Valid AddressRequest address,
        Long preferredBranchId
) {
}