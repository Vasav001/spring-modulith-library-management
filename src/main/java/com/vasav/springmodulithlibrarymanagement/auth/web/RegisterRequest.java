package com.vasav.springmodulithlibrarymanagement.auth.web;

import com.vasav.springmodulithlibrarymanagement.address.api.AddressRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotBlank String username,
        @NotBlank @Email String email,
        @NotBlank @Size(min = 8) String password,
        @NotBlank String firstName,
        String lastName,
        String phone,
        Long preferredBranchId,
        @NotNull @Valid AddressRequest address
) {
}