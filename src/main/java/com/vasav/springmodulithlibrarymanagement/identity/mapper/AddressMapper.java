package com.vasav.springmodulithlibrarymanagement.identity.mapper;

import com.vasav.springmodulithlibrarymanagement.identity.dto.request.AddressRequest;
import com.vasav.springmodulithlibrarymanagement.identity.dto.response.AddressResponse;
import com.vasav.springmodulithlibrarymanagement.shared.address.Address;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", implementationName = "IdentityAddressMapperImpl")
public interface AddressMapper {

    Address toEntity(AddressRequest request);

    AddressResponse toResponse(Address address);

    void updateEntityFromRequest(AddressRequest request, @MappingTarget Address address);
}