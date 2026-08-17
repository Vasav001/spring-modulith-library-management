package com.vasav.springmodulithlibrarymanagement.catalog.mapper;

import com.vasav.springmodulithlibrarymanagement.catalog.dto.request.BookCreateRequest;
import com.vasav.springmodulithlibrarymanagement.catalog.dto.request.BookUpdateRequest;
import com.vasav.springmodulithlibrarymanagement.catalog.dto.response.BookAuthorResponse;
import com.vasav.springmodulithlibrarymanagement.catalog.dto.response.BookResponse;
import com.vasav.springmodulithlibrarymanagement.catalog.entity.Book;
import com.vasav.springmodulithlibrarymanagement.catalog.entity.BookAuthor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface BookMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "publisher", ignore = true)
    @Mapping(target = "authors", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Book toEntity(BookCreateRequest request);

    @Mapping(target = "categoryId", source = "category.id")
    @Mapping(target = "categoryName", source = "category.name")
    @Mapping(target = "publisherId", source = "publisher.id")
    @Mapping(target = "publisherName", source = "publisher.name")
    BookResponse toResponse(Book book);

    @Mapping(target = "id", source = "author.id")
    @Mapping(target = "name", source = "author.name")
    BookAuthorResponse toAuthorResponse(BookAuthor bookAuthor);

    @Mapping(target = "category", ignore = true)
    @Mapping(target = "publisher", ignore = true)
    @Mapping(target = "authors", ignore = true)
    void updateEntityFromRequest(
            BookUpdateRequest request, @MappingTarget Book book
    );
}