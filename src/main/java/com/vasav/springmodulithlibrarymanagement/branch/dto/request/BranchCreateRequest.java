package com.vasav.springmodulithlibrarymanagement.branch.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record BranchCreateRequest(
        @NotBlank @Size(max = 150) String name,
        @NotNull @Valid AddressRequest address,
        @Size(max = 20) String phone,
        @Email @Size(max = 100) String email,
        @Size(max = 100) String openingHours
) {
}