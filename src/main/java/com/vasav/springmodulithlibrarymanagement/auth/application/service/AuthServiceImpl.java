package com.vasav.springmodulithlibrarymanagement.auth.application.service;

import com.vasav.springmodulithlibrarymanagement.address.api.AddressResponse;
import com.vasav.springmodulithlibrarymanagement.address.api.AddressService;
import com.vasav.springmodulithlibrarymanagement.auth.application.exception.InvalidCredentialsException;
import com.vasav.springmodulithlibrarymanagement.auth.web.LoginRequest;
import com.vasav.springmodulithlibrarymanagement.auth.web.RegisterRequest;
import com.vasav.springmodulithlibrarymanagement.auth.web.TokenResponse;
import com.vasav.springmodulithlibrarymanagement.security.JwtService;
import com.vasav.springmodulithlibrarymanagement.user.api.*;
import com.vasav.springmodulithlibrarymanagement.user.api.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl {
    private final UserService userService;
    private final AddressService addressService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public TokenResponse register(RegisterRequest request) {
        AddressResponse addressResponse = addressService.create(request.address());
        UserRegistrationRequest userRegistrationRequest = toRegReq(request, addressResponse.id());

        UserResponse userResponse = userService.registerMember(userRegistrationRequest);
        String accessToken = jwtService.generateAccessToken(userResponse.id(), userResponse.username(), UserRole.MEMBER);
        return new TokenResponse(accessToken, "Bearer", jwtService.getExpirationMs());
    }

    public TokenResponse registerLibrarian(RegisterRequest request) {
        AddressResponse address = addressService.create(request.address());
        UserRegistrationRequest userRequest = toRegReq(request, address.id());

        UserResponse user = userService.createLibrarian(userRequest);
        String accessToken = jwtService.generateAccessToken(user.id(), user.username(), UserRole.LIBRARIAN);
        return new TokenResponse(accessToken, "Bearer", jwtService.getExpirationMs());
    }

    public TokenResponse login(LoginRequest request) {
        UserCredentials credentials;
        try {
            credentials = userService.getCredentialsByUsernameOrEmail(request.usernameOrEmail());
        } catch (UserNotFoundException e) {
            throw new InvalidCredentialsException("Invalid username/email or password");
        }

        if (!passwordEncoder.matches(request.password(), credentials.passwordHash())) {
            throw new InvalidCredentialsException("Invalid username/email or password");
        }
        if (!Boolean.TRUE.equals(credentials.active())) {
            throw new InvalidCredentialsException("Account is deactivated");
        }

        String accessToken = jwtService.generateAccessToken(credentials.id(), credentials.username(), credentials.userRole());
        return new TokenResponse(accessToken, "Bearer", jwtService.getExpirationMs());
    }

    public UserRegistrationRequest toRegReq(RegisterRequest request, Long addressId) {
        return new UserRegistrationRequest(
                request.username(),
                request.email(),
                passwordEncoder.encode(request.password()),
                request.firstName(),
                request.lastName(),
                request.phone(),
                addressId,
                request.preferredBranchId()
        );
    }
}
