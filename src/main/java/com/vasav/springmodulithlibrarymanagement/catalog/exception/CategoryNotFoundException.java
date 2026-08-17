package com.vasav.springmodulithlibrarymanagement.catalog.exception;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(String message) {
        super(message);
    }
}