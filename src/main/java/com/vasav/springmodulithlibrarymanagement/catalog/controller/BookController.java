package com.vasav.springmodulithlibrarymanagement.catalog.controller;

import com.vasav.springmodulithlibrarymanagement.catalog.dto.request.BookCreateRequest;
import com.vasav.springmodulithlibrarymanagement.catalog.dto.request.BookUpdateRequest;
import com.vasav.springmodulithlibrarymanagement.catalog.dto.response.BookResponse;
import com.vasav.springmodulithlibrarymanagement.catalog.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping
    public List<BookResponse> getActiveBooks() {
        return bookService.getActiveBooks();
    }

    @GetMapping("/{id}")
    public BookResponse getBookById(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('LIBRARIAN', 'ADMIN')")
    public List<BookResponse> getAllBooks() {
        return bookService.getAllBooks();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('LIBRARIAN', 'ADMIN')")
    public BookResponse createBook(@Valid @RequestBody BookCreateRequest request) {
        return bookService.createBook(request);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('LIBRARIAN', 'ADMIN')")
    public BookResponse updateBook(@PathVariable Long id, @Valid @RequestBody BookUpdateRequest request) {
        return bookService.updateBook(id, request);
    }

    @PatchMapping("/{id}/deactivate")
    @PreAuthorize("hasAnyRole('LIBRARIAN', 'ADMIN')")
    public BookResponse deactivateBook(@PathVariable Long id) {
        return bookService.setActive(id, false);
    }

    @PatchMapping("/{id}/activate")
    @PreAuthorize("hasAnyRole('LIBRARIAN', 'ADMIN')")
    public BookResponse activateBook(@PathVariable Long id) {
        return bookService.setActive(id, true);
    }
}