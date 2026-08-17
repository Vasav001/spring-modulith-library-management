package com.vasav.springmodulithlibrarymanagement.catalog.exception;

public class BookCopyNotFoundException extends RuntimeException {
    public BookCopyNotFoundException(String message) {
        super(message);
    }
}