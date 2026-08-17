package com.vasav.springmodulithlibrarymanagement.identity.exception;

public class InvalidTokenException extends RuntimeException {

    public InvalidTokenException(String message) {
        super(message);
    }
}