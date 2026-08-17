package com.vasav.springmodulithlibrarymanagement.circulation.dto.response;

import com.vasav.springmodulithlibrarymanagement.circulation.entity.FineReason;

import java.math.BigDecimal;
import java.time.Instant;

public record FineResponse(
        Long id,
        Long loanId,
        BigDecimal amount,
        FineReason reason,
        Instant paidAt,
        Instant createdAt
) {
}