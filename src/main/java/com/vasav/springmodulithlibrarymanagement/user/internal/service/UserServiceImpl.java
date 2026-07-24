package com.vasav.springmodulithlibrarymanagement.user.internal.service;

import com.vasav.springmodulithlibrarymanagement.address.api.AddressResponse;
import com.vasav.springmodulithlibrarymanagement.address.api.AddressService;
import com.vasav.springmodulithlibrarymanagement.user.api.*;
import com.vasav.springmodulithlibrarymanagement.user.api.exception.DuplicateUserException;
import com.vasav.springmodulithlibrarymanagement.user.api.exception.UserNotFoundException;
import com.vasav.springmodulithlibrarymanagement.user.internal.entity.User;
import com.vasav.springmodulithlibrarymanagement.user.internal.mapper.UserMapper;
import com.vasav.springmodulithlibrarymanagement.user.internal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AddressService addressService;

    @Override
    public Page<UserSummary> getAll(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(userMapper::toSummary);
    }

    @Override
    public Page<UserSummary> search(String query, Pageable pageable) {
        return userRepository.search(query, pageable)
                .map(userMapper::toSummary);
    }

    @Override
    public UserSummary getByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));
        return userMapper.toSummary(user);
    }

    @Override
    public UserSummary getByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));
        return userMapper.toSummary(user);
    }

    @Override
    public UserSummary getSummary(Long id) {
        User user = findUserOrThrow(id);
        return userMapper.toSummary(user);
    }

    @Override
    @Transactional
    public void activate(Long id) {
        User user = findUserOrThrow(id);
        user.setActive(true);
    }

    @Override
    @Transactional
    public void deactivate(Long id) {
        User user = findUserOrThrow(id);
        user.setActive(false);
    }

    @Override
    @Transactional
    public void changeRole(Long id, UserRole role) {
        User user = findUserOrThrow(id);
        user.setUserRole(role);
    }

    @Override
    @Transactional
    public UserResponse create(UserRequest request) {
        if (userRepository.existsByUsername(request.username())) {
            throw new DuplicateUserException("Username already in use: " + request.username());
        }
        if (userRepository.existsByEmail(request.email())) {
            throw new DuplicateUserException("Email already in use: " + request.email());
        }

        AddressResponse address = addressService.create(request.address());

        User user = userMapper.toEntity(request);
        user.setAddressId(address.id());
        User saved = userRepository.save(user);
        return userMapper.toResponse(saved, address);
    }

    @Override
    public UserResponse getById(Long id) {
        User user = findUserOrThrow(id);
        return userMapper.toResponse(user, addressService.getById(user.getAddressId()));
    }

    @Override
    @Transactional
    public UserResponse update(Long id, UserRequest request) {
        User user = findUserOrThrow(id);
        if (!user.getUsername().equals(request.username())
                && userRepository.existsByUsername(request.username())) {
            throw new DuplicateUserException("Username already in use: " + request.username());
        }
        if (!user.getEmail().equals(request.email())
                && userRepository.existsByEmail(request.email())) {
            throw new DuplicateUserException("Email already in use: " + request.email());
        }

        AddressResponse address = addressService.update(
                user.getAddressId(),
                request.address()
        );

        userMapper.updateEntity(user, request);
        return userMapper.toResponse(user, address);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
    }

    private User findUserOrThrow(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    @Transactional
    public UserResponse registerMember(UserRegistrationRequest request) {
        if (userRepository.existsByUsername(request.username())) {
            throw new DuplicateUserException("Username already in use: " + request.username());
        }
        if (userRepository.existsByEmail(request.email())) {
            throw new DuplicateUserException("Email already in use: " + request.email());
        }

        AddressResponse addressResponse = addressService.getById(request.addressId());
        User user = userMapper.toEntityForRegistration(request);
        User saved = userRepository.save(user);
        return userMapper.toResponse(saved, addressResponse);
    }

    @Override
    @Transactional
    public UserResponse createLibrarian(UserRegistrationRequest request) {
        if (userRepository.existsByUsername(request.username())) {
            throw new DuplicateUserException("Username already in use: " + request.username());
        }
        if (userRepository.existsByEmail(request.email())) {
            throw new DuplicateUserException("Email already in use: " + request.email());
        }

        AddressResponse addressResponse = addressService.getById(request.addressId());
        User user = userMapper.toEntityForLibrarian(request);
        User saved = userRepository.save(user);
        return userMapper.toResponse(saved, addressResponse);
    }

    @Override
    public UserCredentials getCredentialsByUsernameOrEmail(String usernameOrEmail) {
        User user = userRepository.findByUsername(usernameOrEmail)
                .or(() -> userRepository.findByEmail(usernameOrEmail))
                .orElseThrow(() -> new UserNotFoundException("User not found: " + usernameOrEmail));
        return userMapper.toCredentials(user);
    }
}