package com.vasav.springmodulithlibrarymanagement.circulation.dto.request;

import jakarta.validation.constraints.NotNull;

public record PayFineRequest(
        @NotNull Long fineId
) {
}