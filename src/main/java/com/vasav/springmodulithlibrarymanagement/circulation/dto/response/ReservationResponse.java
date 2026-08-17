package com.vasav.springmodulithlibrarymanagement.circulation.dto.response;

import com.vasav.springmodulithlibrarymanagement.circulation.entity.ReservationStatus;

import java.time.Instant;

public record ReservationResponse(
        Long id,
        Long bookId,
        Long userId,
        Long pickupBranchId,
        Long readyCopyId,
        Instant reservationDate,
        ReservationStatus status,
        Instant readyUntil
) {
}