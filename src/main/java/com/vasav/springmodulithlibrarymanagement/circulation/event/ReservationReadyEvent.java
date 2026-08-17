package com.vasav.springmodulithlibrarymanagement.circulation.event;

import java.time.Instant;

public record ReservationReadyEvent(
        Long reservationId,
        Long userId,
        Long bookId,
        Long pickupBranchId,
        Instant readyUntil
) {
}