package com.vasav.springmodulithlibrarymanagement.catalog.dto.request;

import com.vasav.springmodulithlibrarymanagement.catalog.entity.BookCopyStatus;
import com.vasav.springmodulithlibrarymanagement.catalog.entity.PhysicalCondition;
import jakarta.validation.constraints.Size;

public record BookCopyUpdateRequest(
        Long branchId,
        @Size(max = 50) String barcode,
        PhysicalCondition physicalCondition
) {
}