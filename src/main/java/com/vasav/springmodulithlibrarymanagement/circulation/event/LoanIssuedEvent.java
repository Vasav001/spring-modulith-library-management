package com.vasav.springmodulithlibrarymanagement.circulation.event;

import java.time.Instant;

public record LoanIssuedEvent(
        Long loanId,
        Long userId,
        Long bookCopyId,
        Long branchId,
        Instant dueDate
) {
}