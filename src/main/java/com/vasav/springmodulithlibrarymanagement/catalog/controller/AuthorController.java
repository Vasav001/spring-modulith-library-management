package com.vasav.springmodulithlibrarymanagement.catalog.controller;

import com.vasav.springmodulithlibrarymanagement.catalog.dto.request.AuthorCreateRequest;
import com.vasav.springmodulithlibrarymanagement.catalog.dto.request.AuthorUpdateRequest;
import com.vasav.springmodulithlibrarymanagement.catalog.dto.response.AuthorResponse;
import com.vasav.springmodulithlibrarymanagement.catalog.service.AuthorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping
    public List<AuthorResponse> getAllAuthors() {
        return authorService.getAllAuthors();
    }

    @GetMapping("/{id}")
    public AuthorResponse getAuthorById(@PathVariable Long id) {
        return authorService.getAuthorById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('LIBRARIAN', 'ADMIN')")
    public AuthorResponse createAuthor(@Valid @RequestBody AuthorCreateRequest request) {
        return authorService.createAuthor(request);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('LIBRARIAN', 'ADMIN')")
    public AuthorResponse updateAuthor(@PathVariable Long id, @Valid @RequestBody AuthorUpdateRequest request) {
        return authorService.updateAuthor(id, request);
    }
}