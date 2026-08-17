package com.vasav.springmodulithlibrarymanagement.circulation.dto.request;

import jakarta.validation.constraints.NotNull;

public record ReturnLoanRequest(
        @NotNull Long returnBranchId,
        @NotNull Long returnedBy
) {
}