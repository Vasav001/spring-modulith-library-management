package com.vasav.springmodulithlibrarymanagement.catalog.exception;

public class DuplicateIsbnException extends RuntimeException {
    public DuplicateIsbnException(String message) {
        super(message);
    }
}