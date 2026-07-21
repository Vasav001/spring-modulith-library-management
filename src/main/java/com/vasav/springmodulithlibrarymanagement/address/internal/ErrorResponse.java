package com.vasav.springmodulithlibrarymanagement.address.internal;

import java.time.Instant;

record ErrorResponse(
        Instant timestamp,
        int status,
        String error,
        String message,
        String path
) {
}