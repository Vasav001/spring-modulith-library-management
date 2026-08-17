package com.vasav.springmodulithlibrarymanagement.catalog.dto.request;

import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record AuthorUpdateRequest(
        @Size(max = 200) String name,
        String biography,
        LocalDate dateOfBirth,
        @Size(max = 100) String nationality
) {
}