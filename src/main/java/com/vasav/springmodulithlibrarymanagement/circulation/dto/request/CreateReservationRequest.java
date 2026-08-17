package com.vasav.springmodulithlibrarymanagement.circulation.dto.request;

import jakarta.validation.constraints.NotNull;

public record CreateReservationRequest(
        @NotNull Long bookId,
        @NotNull Long pickupBranchId
) {
}