package com.vasav.springmodulithlibrarymanagement.catalog.dto.request;

import jakarta.validation.constraints.*;

import java.util.List;

public record BookCreateRequest(
        @NotBlank @Size(max = 300) String title,

        @Size(max = 17)
        String isbn,

        @NotNull
        Long categoryId,

        Long publisherId,

        @Min(600)
        @Max(9999)
        Short publicationYear,

        String description,

        @Size(max = 50)
        String language,

        @Min(1)
        Integer pages,

        @NotEmpty
        List<Long> authorIds
) {
}