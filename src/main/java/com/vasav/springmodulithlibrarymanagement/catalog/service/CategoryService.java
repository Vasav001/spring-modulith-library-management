package com.vasav.springmodulithlibrarymanagement.catalog.service;

import com.vasav.springmodulithlibrarymanagement.catalog.dto.request.CategoryCreateRequest;
import com.vasav.springmodulithlibrarymanagement.catalog.dto.request.CategoryUpdateRequest;
import com.vasav.springmodulithlibrarymanagement.catalog.dto.response.CategoryResponse;
import com.vasav.springmodulithlibrarymanagement.catalog.entity.Category;
import com.vasav.springmodulithlibrarymanagement.catalog.exception.CategoryNotFoundException;
import com.vasav.springmodulithlibrarymanagement.catalog.mapper.CategoryMapper;
import com.vasav.springmodulithlibrarymanagement.catalog.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Transactional
    public CategoryResponse createCategory(CategoryCreateRequest request) {
        return categoryMapper.toResponse(categoryRepository.save(categoryMapper.toEntity(request)));
    }

    @Transactional
    public CategoryResponse updateCategory(Long id, CategoryUpdateRequest request) {
        Category category = getCategory(id);
        categoryMapper.updateEntityFromRequest(request, category);
        return categoryMapper.toResponse(
                categoryRepository.save(category)
        );
    }

    @Transactional(readOnly = true)
    public CategoryResponse getCategoryById(Long id) {
        return categoryMapper.toResponse(getCategory(id));
    }

    @Transactional(readOnly = true)
    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toResponse)
                .toList();
    }

    private Category getCategory(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found for id: " + id));
    }
}