package com.vasav.springmodulithlibrarymanagement.catalog.mapper;

import com.vasav.springmodulithlibrarymanagement.catalog.dto.request.AuthorCreateRequest;
import com.vasav.springmodulithlibrarymanagement.catalog.dto.request.AuthorUpdateRequest;
import com.vasav.springmodulithlibrarymanagement.catalog.dto.response.AuthorResponse;
import com.vasav.springmodulithlibrarymanagement.catalog.entity.Author;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface AuthorMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Author toEntity(AuthorCreateRequest request);

    AuthorResponse toResponse(Author author);

    void updateEntityFromRequest(
            AuthorUpdateRequest request, @MappingTarget Author author
    );
}