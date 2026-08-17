package com.vasav.springmodulithlibrarymanagement.catalog.controller;

import com.vasav.springmodulithlibrarymanagement.catalog.dto.request.PublisherCreateRequest;
import com.vasav.springmodulithlibrarymanagement.catalog.dto.request.PublisherUpdateRequest;
import com.vasav.springmodulithlibrarymanagement.catalog.dto.response.PublisherResponse;
import com.vasav.springmodulithlibrarymanagement.catalog.service.PublisherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/publishers")
@RequiredArgsConstructor
public class PublisherController {

    private final PublisherService publisherService;

    @GetMapping
    public List<PublisherResponse> getAllPublishers() {
        return publisherService.getAllPublishers();
    }

    @GetMapping("/{id}")
    public PublisherResponse getPublisherById(@PathVariable Long id) {
        return publisherService.getPublisherById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('LIBRARIAN', 'ADMIN')")
    public PublisherResponse createPublisher(@Valid @RequestBody PublisherCreateRequest request) {
        return publisherService.createPublisher(request);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('LIBRARIAN', 'ADMIN')")
    public PublisherResponse updatePublisher(@PathVariable Long id, @Valid @RequestBody PublisherUpdateRequest request) {
        return publisherService.updatePublisher(id, request);
    }
}