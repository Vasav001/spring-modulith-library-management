package com.vasav.springmodulithlibrarymanagement.catalog.exception;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(String message) {
        super(message);
    }
}