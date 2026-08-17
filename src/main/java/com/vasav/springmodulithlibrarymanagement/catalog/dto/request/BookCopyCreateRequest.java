package com.vasav.springmodulithlibrarymanagement.catalog.dto.request;

import com.vasav.springmodulithlibrarymanagement.catalog.entity.PhysicalCondition;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record BookCopyCreateRequest(
        @NotNull Long bookId,
        @NotNull Long branchId,
        @Size(max = 50) String barcode,
        PhysicalCondition physicalCondition,
        LocalDate acquisitionDate
) {
}