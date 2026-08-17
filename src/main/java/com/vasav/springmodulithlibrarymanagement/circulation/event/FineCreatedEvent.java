package com.vasav.springmodulithlibrarymanagement.circulation.event;

import com.vasav.springmodulithlibrarymanagement.circulation.entity.FineReason;

import java.math.BigDecimal;

public record FineCreatedEvent(
        Long fineId,
        Long loanId,
        Long userId,
        BigDecimal amount,
        FineReason reason
) {
}