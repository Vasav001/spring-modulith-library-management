package com.vasav.springmodulithlibrarymanagement.catalog.dto.response;

public record BookAuthorResponse(
        Long id,
        String name,
        Short authorOrder
) {
}