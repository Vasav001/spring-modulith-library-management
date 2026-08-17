package com.vasav.springmodulithlibrarymanagement.catalog.dto.response;

import com.vasav.springmodulithlibrarymanagement.catalog.entity.BookCopyStatus;
import com.vasav.springmodulithlibrarymanagement.catalog.entity.PhysicalCondition;

import java.time.Instant;
import java.time.LocalDate;

public record BookCopyResponse(
        Long id,
        Long bookId,
        String bookTitle,
        Long branchId,
        String barcode,
        BookCopyStatus status,
        PhysicalCondition physicalCondition,
        LocalDate acquisitionDate,
        Instant createdAt,
        Instant updatedAt
) {
}