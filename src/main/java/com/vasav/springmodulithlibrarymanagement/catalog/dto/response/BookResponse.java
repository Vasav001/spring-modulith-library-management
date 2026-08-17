package com.vasav.springmodulithlibrarymanagement.catalog.dto.response;

import java.time.Instant;
import java.util.List;

public record BookResponse(
        Long id,
        String title,
        String isbn,
        Long categoryId,
        String categoryName,
        Long publisherId,
        String publisherName,
        Short publicationYear,
        String description,
        String language,
        Integer pages,
        Boolean active,
        List<BookAuthorResponse> authors,
        Instant createdAt,
        Instant updatedAt
) {
}