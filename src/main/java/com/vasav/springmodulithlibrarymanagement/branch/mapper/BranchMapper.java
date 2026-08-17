package com.vasav.springmodulithlibrarymanagement.branch.mapper;

import com.vasav.springmodulithlibrarymanagement.branch.dto.request.BranchCreateRequest;
import com.vasav.springmodulithlibrarymanagement.branch.dto.request.BranchUpdateRequest;
import com.vasav.springmodulithlibrarymanagement.branch.dto.response.BranchResponse;
import com.vasav.springmodulithlibrarymanagement.branch.entity.Branch;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        uses = AddressMapper.class,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface BranchMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "address", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Branch toEntity(BranchCreateRequest request);

    BranchResponse toResponse(Branch branch);

    @Mapping(target = "address", ignore = true)
    void updateEntityFromRequest(BranchUpdateRequest request, @MappingTarget Branch branch);
}
