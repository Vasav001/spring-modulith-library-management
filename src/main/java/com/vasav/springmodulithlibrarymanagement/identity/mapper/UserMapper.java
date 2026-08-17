package com.vasav.springmodulithlibrarymanagement.identity.mapper;

import com.vasav.springmodulithlibrarymanagement.identity.dto.request.RegisterRequest;
import com.vasav.springmodulithlibrarymanagement.identity.dto.request.UpdateProfileRequest;
import com.vasav.springmodulithlibrarymanagement.identity.dto.response.UserResponse;
import com.vasav.springmodulithlibrarymanagement.identity.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = AddressMapper.class)
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "passwordHash", ignore = true)
    @Mapping(target = "address", ignore = true)
    @Mapping(target = "addressVerifiedAt", ignore = true)
    @Mapping(target = "emailVerifiedAt", ignore = true)
    @Mapping(target = "preferredBranchId", ignore = true)
    @Mapping(target = "userRole", ignore = true)
    @Mapping(target = "accountStatus", ignore = true)
    @Mapping(target = "lastLoginAt", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    User toEntity(RegisterRequest request);

    @Mapping(target = "role", source = "userRole")
    @Mapping(target = "emailVerified", expression = "java(user.getEmailVerifiedAt() != null)")
    @Mapping(target = "addressVerified", expression = "java(user.getAddressVerifiedAt() != null)")
    UserResponse toResponse(User user);

    @Mapping(target = "address", ignore = true)
    @Mapping(target = "preferredBranchId", ignore = true)
    void updateEntityFromRequest(UpdateProfileRequest request, @MappingTarget User user);
}
