package com.vasav.springmodulithlibrarymanagement.catalog.dto.response;

import java.time.Instant;
import java.time.LocalDate;

public record AuthorResponse(
        Long id,
        String name,
        String biography,
        LocalDate dateOfBirth,
        String nationality,
        Instant createdAt,
        Instant updatedAt
) {
}