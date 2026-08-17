package com.vasav.springmodulithlibrarymanagement.circulation.event;

import java.time.Instant;

public record LoanReturnedEvent(
        Long loanId,
        Long userId,
        Long bookCopyId,
        Long borrowBranchId,
        Long returnBranchId,
        Instant dueDate,
        Instant returnDate
) {
}