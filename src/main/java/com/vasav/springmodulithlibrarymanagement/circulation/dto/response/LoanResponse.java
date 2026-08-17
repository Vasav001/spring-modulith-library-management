package com.vasav.springmodulithlibrarymanagement.circulation.dto.response;

import com.vasav.springmodulithlibrarymanagement.circulation.entity.LoanStatus;

import java.time.Instant;

public record LoanResponse(
        Long id,
        Long bookCopyId,
        Long userId,
        Long borrowBranchId,
        Long returnBranchId,
        Long issuedBy,
        Long returnedBy,
        Instant loanDate,
        Instant dueDate,
        Instant returnDate,
        LoanStatus status
) {
}