package com.vasav.springmodulithlibrarymanagement.catalog.mapper;

import com.vasav.springmodulithlibrarymanagement.catalog.dto.request.BookCopyCreateRequest;
import com.vasav.springmodulithlibrarymanagement.catalog.dto.request.BookCopyUpdateRequest;
import com.vasav.springmodulithlibrarymanagement.catalog.dto.response.BookCopyResponse;
import com.vasav.springmodulithlibrarymanagement.catalog.entity.BookCopy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface BookCopyMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "book", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    BookCopy toEntity(BookCopyCreateRequest request);

    @Mapping(target = "bookId", source = "book.id")
    @Mapping(target = "bookTitle", source = "book.title")
    BookCopyResponse toResponse(BookCopy bookCopy);

    @Mapping(target = "book", ignore = true)
    void updateEntityFromRequest(
            BookCopyUpdateRequest request, @MappingTarget BookCopy bookCopy
    );
}