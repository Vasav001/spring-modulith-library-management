package com.vasav.springmodulithlibrarymanagement.catalog.exception;

public class AuthorNotFoundException extends RuntimeException {

    public AuthorNotFoundException(String message) {
        super(message);
    }
}