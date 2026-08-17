package com.vasav.springmodulithlibrarymanagement.catalog.controller;

import com.vasav.springmodulithlibrarymanagement.catalog.dto.request.CategoryCreateRequest;
import com.vasav.springmodulithlibrarymanagement.catalog.dto.request.CategoryUpdateRequest;
import com.vasav.springmodulithlibrarymanagement.catalog.dto.response.CategoryResponse;
import com.vasav.springmodulithlibrarymanagement.catalog.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public List<CategoryResponse> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    public CategoryResponse getCategoryById(@PathVariable Long id) {
        return categoryService.getCategoryById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('LIBRARIAN', 'ADMIN')")
    public CategoryResponse createCategory(@Valid @RequestBody CategoryCreateRequest request) {
        return categoryService.createCategory(request);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('LIBRARIAN', 'ADMIN')")
    public CategoryResponse updateCategory(@PathVariable Long id, @Valid @RequestBody CategoryUpdateRequest request) {
        return categoryService.updateCategory(id, request);
    }
}