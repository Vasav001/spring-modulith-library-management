package com.vasav.springmodulithlibrarymanagement.branch.mapper;

import com.vasav.springmodulithlibrarymanagement.branch.dto.request.AddressRequest;
import com.vasav.springmodulithlibrarymanagement.branch.dto.response.AddressResponse;
import com.vasav.springmodulithlibrarymanagement.shared.address.Address;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", implementationName = "BranchAddressMapperImpl")
public interface AddressMapper {

    Address toEntity(AddressRequest request);

    AddressResponse toResponse(Address address);

    void updateEntityFromRequest(AddressRequest request, @MappingTarget Address address);
}