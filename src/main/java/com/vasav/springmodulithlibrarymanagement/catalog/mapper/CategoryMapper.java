package com.vasav.springmodulithlibrarymanagement.catalog.mapper;

import com.vasav.springmodulithlibrarymanagement.catalog.dto.request.CategoryCreateRequest;
import com.vasav.springmodulithlibrarymanagement.catalog.dto.request.CategoryUpdateRequest;
import com.vasav.springmodulithlibrarymanagement.catalog.dto.response.CategoryResponse;
import com.vasav.springmodulithlibrarymanagement.catalog.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface CategoryMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Category toEntity(CategoryCreateRequest request);

    CategoryResponse toResponse(Category category);

    void updateEntityFromRequest(
            CategoryUpdateRequest request, @MappingTarget Category category
    );
}