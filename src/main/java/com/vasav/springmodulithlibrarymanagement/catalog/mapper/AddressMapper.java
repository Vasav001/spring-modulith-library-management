package com.vasav.springmodulithlibrarymanagement.catalog.mapper;

import com.vasav.springmodulithlibrarymanagement.catalog.dto.request.AddressRequest;
import com.vasav.springmodulithlibrarymanagement.catalog.dto.response.AddressResponse;
import com.vasav.springmodulithlibrarymanagement.shared.address.Address;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", implementationName = "CatalogAddressMapperImpl")
public interface AddressMapper {

    Address toEntity(AddressRequest request);

    AddressResponse toResponse(Address address);

    void updateEntityFromRequest(AddressRequest request, @MappingTarget Address address);
}