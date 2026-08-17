package com.vasav.springmodulithlibrarymanagement.circulation.event;

public record ReservationCancelledEvent(
        Long reservationId,
        Long userId,
        String reason
) {
}