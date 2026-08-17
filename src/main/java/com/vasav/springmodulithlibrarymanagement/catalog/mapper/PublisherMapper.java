package com.vasav.springmodulithlibrarymanagement.catalog.mapper;

import com.vasav.springmodulithlibrarymanagement.catalog.dto.request.PublisherCreateRequest;
import com.vasav.springmodulithlibrarymanagement.catalog.dto.request.PublisherUpdateRequest;
import com.vasav.springmodulithlibrarymanagement.catalog.dto.response.PublisherResponse;
import com.vasav.springmodulithlibrarymanagement.catalog.entity.Publisher;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        uses = AddressMapper.class,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface PublisherMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "address", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Publisher toEntity(PublisherCreateRequest request);

    PublisherResponse toResponse(Publisher publisher);

    @Mapping(target = "address", ignore = true)
    void updateEntityFromRequest(
            PublisherUpdateRequest request,
            @MappingTarget Publisher publisher
    );
}