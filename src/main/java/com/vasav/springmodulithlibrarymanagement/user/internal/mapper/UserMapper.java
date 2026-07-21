package com.vasav.springmodulithlibrarymanagement.user.internal.mapper;

import com.vasav.springmodulithlibrarymanagement.user.api.UserRequest;
import com.vasav.springmodulithlibrarymanagement.user.api.UserResponse;
import com.vasav.springmodulithlibrarymanagement.user.api.UserSummary;
import com.vasav.springmodulithlibrarymanagement.user.internal.entity.User;
import org.springframework.stereotype.Component;

@Component
class UserMapper {

    User toEntity(UserRequest request) {
        User user = new User();

        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setPhone(request.phone());
        user.setAddressId(request.addressId());
        user.setPreferredBranchId(request.preferredBranchId());
        user.setUserRole(request.userRole());
        user.setActive(request.active());

        return user;
    }

    void updateEntity(User user, UserRequest request) {
        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setPhone(request.phone());
        user.setAddressId(request.addressId());
        user.setPreferredBranchId(request.preferredBranchId());
        user.setUserRole(request.userRole());
        user.setActive(request.active());
    }

    UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getPhone(),
                user.getAddressId(),
                user.getPreferredBranchId(),
                user.getUserRole(),
                user.getActive(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }

    UserSummary toSummary(User user) {
        return new UserSummary(
                user.getId(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail()
        );
    }
}