package com.vasav.springmodulithlibrarymanagement.catalog.event;

public record BookCopyAvailableEvent(
        Long copyId,
        Long bookId,
        Long branchId
) {
}