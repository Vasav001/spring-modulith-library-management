package com.vasav.springmodulithlibrarymanagement.catalog.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

import java.util.List;

public record BookUpdateRequest(
        @Size(max = 300)
        String title,

        @Size(max = 17)
        String isbn,

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

        List<Long> authorIds,

        Boolean active
) {
}