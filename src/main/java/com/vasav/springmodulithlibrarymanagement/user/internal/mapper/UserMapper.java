package com.vasav.springmodulithlibrarymanagement.user.internal.mapper;

import com.vasav.springmodulithlibrarymanagement.address.api.AddressResponse;
import com.vasav.springmodulithlibrarymanagement.user.api.*;
import com.vasav.springmodulithlibrarymanagement.user.internal.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toEntity(UserRequest request) {
        User user = new User();

        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setPhone(request.phone());
        user.setPreferredBranchId(request.preferredBranchId());
        user.setUserRole(request.userRole());
        user.setActive(request.active());

        return user;
    }

    public void updateEntity(User user, UserRequest request) {
        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setPhone(request.phone());
        user.setPreferredBranchId(request.preferredBranchId());
        user.setUserRole(request.userRole());
        user.setActive(request.active());
    }

    public UserResponse toResponse(User user, AddressResponse addressResponse) {
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getPhone(),
                addressResponse,
                user.getPreferredBranchId(),
                user.getUserRole(),
                user.getActive(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }

    public UserSummary toSummary(User user) {
        return new UserSummary(
                user.getId(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail()
        );
    }

    public User toEntityForRegistration(UserRegistrationRequest request) {
        User user = new User();
        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setPasswordHash(request.passwordHash());
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setPhone(request.phone());
        user.setAddressId(request.addressId());
        user.setPreferredBranchId(request.preferredBranchId());
        user.setUserRole(UserRole.MEMBER);
        user.setActive(true);
        return user;
    }

    public User toEntityForLibrarian(UserRegistrationRequest request) {
        User user = toEntityForRegistration(request);
        user.setUserRole(UserRole.LIBRARIAN);
        return user;
    }

    public UserCredentials toCredentials(User user) {
        return new UserCredentials(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPasswordHash(),
                user.getUserRole(),
                user.getActive()
        );
    }
}