package com.vasav.springmodulithlibrarymanagement.catalog.controller;

import com.vasav.springmodulithlibrarymanagement.catalog.dto.request.BookCopyCreateRequest;
import com.vasav.springmodulithlibrarymanagement.catalog.dto.request.BookCopyUpdateRequest;
import com.vasav.springmodulithlibrarymanagement.catalog.dto.response.BookCopyResponse;
import com.vasav.springmodulithlibrarymanagement.catalog.service.BookCopyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book-copies")
@RequiredArgsConstructor
public class BookCopyController {

    private final BookCopyService bookCopyService;

    @GetMapping("/{id}")
    public BookCopyResponse getBookCopyById(@PathVariable Long id) {
        return bookCopyService.getBookCopyById(id);
    }

    @GetMapping("/book/{bookId}")
    public List<BookCopyResponse> getBookCopiesByBookId(@PathVariable Long bookId) {
        return bookCopyService.getBookCopiesByBookId(bookId);
    }

    @GetMapping("/branch/{branchId}")
    public List<BookCopyResponse> getBookCopiesByBranchId(@PathVariable Long branchId) {
        return bookCopyService.getBookCopiesByBranchId(branchId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('LIBRARIAN', 'ADMIN')")
    public BookCopyResponse createBookCopy(@Valid @RequestBody BookCopyCreateRequest request) {
        return bookCopyService.createBookCopy(request);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('LIBRARIAN', 'ADMIN')")
    public BookCopyResponse updateBookCopy(@PathVariable Long id, @Valid @RequestBody BookCopyUpdateRequest request) {
        return bookCopyService.updateBookCopy(id, request);
    }
}