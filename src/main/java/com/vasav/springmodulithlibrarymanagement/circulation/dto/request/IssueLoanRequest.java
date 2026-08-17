package com.vasav.springmodulithlibrarymanagement.circulation.dto.request;

import jakarta.validation.constraints.NotNull;

import java.time.Instant;

public record IssueLoanRequest(
        @NotNull Long bookId,
        @NotNull Long borrowBranchId,
        @NotNull Instant dueDate
) {
}