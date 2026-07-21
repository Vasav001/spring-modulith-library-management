package com.vasav.springmodulithlibrarymanagement.user.internal.exception;

public class DuplicateUserException extends RuntimeException {

    public DuplicateUserException(String message) {
        super(message);
    }
}