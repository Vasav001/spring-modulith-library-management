package com.vasav.springmodulithlibrarymanagement.identity.service;

import com.vasav.springmodulithlibrarymanagement.identity.dto.request.UpdateProfileRequest;
import com.vasav.springmodulithlibrarymanagement.identity.dto.response.UserResponse;
import com.vasav.springmodulithlibrarymanagement.identity.entity.AccountStatus;
import com.vasav.springmodulithlibrarymanagement.identity.entity.User;
import com.vasav.springmodulithlibrarymanagement.identity.exception.UserNotFoundException;
import com.vasav.springmodulithlibrarymanagement.identity.mapper.AddressMapper;
import com.vasav.springmodulithlibrarymanagement.identity.mapper.UserMapper;
import com.vasav.springmodulithlibrarymanagement.identity.repository.UserRepository;
import com.vasav.springmodulithlibrarymanagement.identity.security.SecurityUtil;
import com.vasav.springmodulithlibrarymanagement.shared.address.Address;
import com.vasav.springmodulithlibrarymanagement.shared.address.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AddressMapper addressMapper;
    private final AddressRepository addressRepository;

    @Transactional(readOnly = true)
    public UserResponse getCurrentUserProfile() {
        return userMapper.toResponse(SecurityUtil.getCurrentUser());
    }

    @Transactional(readOnly = true)
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found for id: " + id));
        return userMapper.toResponse(user);
    }

    @Transactional
    public UserResponse updateCurrentUserProfile(UpdateProfileRequest request) {
        User user = userRepository.findById(SecurityUtil.getCurrentUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        userMapper.updateEntityFromRequest(request, user);

        if (request.address() != null) {
            Address address = user.getAddress();

            if (address == null) {
                address = addressMapper.toEntity(request.address());
            } else {
                addressMapper.updateEntityFromRequest(request.address(), address);
            }

            user.setAddress(addressRepository.save(address));
            user.setAddressVerifiedAt(null);
        }

        if (request.preferredBranchId() != null) {
            user.setPreferredBranchId(request.preferredBranchId());
        }

        if (user.getAccountStatus() == AccountStatus.PENDING_PROFILE && user.getAddress() != null) {
            user.setAccountStatus(AccountStatus.ACTIVE);
        }

        return userMapper.toResponse(userRepository.save(user));
    }
}
