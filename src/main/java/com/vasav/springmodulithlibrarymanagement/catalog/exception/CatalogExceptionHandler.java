package com.vasav.springmodulithlibrarymanagement.catalog.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice(
        basePackages = "com.vasav.springmodulithlibrarymanagement.catalog.controller"
)
public class CatalogExceptionHandler {

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleBookNotFound(BookNotFoundException ex, HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                Instant.now(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(errorResponse);
    }

    @ExceptionHandler(AuthorNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleAuthorNotFound(AuthorNotFoundException ex, HttpServletRequest request) {
        return buildErrorResponse(
                ex, request, HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCategoryNotFound(CategoryNotFoundException ex, HttpServletRequest request) {
        return buildErrorResponse(
                ex, request, HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(PublisherNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePublisherNotFound(PublisherNotFoundException ex, HttpServletRequest request) {
        return buildErrorResponse(
                ex, request, HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(BookCopyNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleBookCopyNotFound(BookCopyNotFoundException ex, HttpServletRequest request) {
        return buildErrorResponse(
                ex, request, HttpStatus.NOT_FOUND
        );
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(RuntimeException ex, HttpServletRequest request, HttpStatus status) {
        ErrorResponse errorResponse = new ErrorResponse(
                Instant.now(),
                status.value(),
                status.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity
                .status(status)
                .body(errorResponse);
    }
}