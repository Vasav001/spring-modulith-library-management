package com.vasav.springmodulithlibrarymanagement.identity.service;

import com.vasav.springmodulithlibrarymanagement.identity.dto.request.LoginRequest;
import com.vasav.springmodulithlibrarymanagement.identity.dto.request.RefreshTokenRequest;
import com.vasav.springmodulithlibrarymanagement.identity.dto.request.RegisterRequest;
import com.vasav.springmodulithlibrarymanagement.identity.dto.response.AuthResponse;
import com.vasav.springmodulithlibrarymanagement.identity.dto.response.UserResponse;
import com.vasav.springmodulithlibrarymanagement.identity.entity.AccountStatus;
import com.vasav.springmodulithlibrarymanagement.identity.entity.User;
import com.vasav.springmodulithlibrarymanagement.identity.entity.UserRole;
import com.vasav.springmodulithlibrarymanagement.identity.exception.EmailAlreadyExistsException;
import com.vasav.springmodulithlibrarymanagement.identity.exception.InvalidTokenException;
import com.vasav.springmodulithlibrarymanagement.identity.exception.UserNotFoundException;
import com.vasav.springmodulithlibrarymanagement.identity.mapper.UserMapper;
import com.vasav.springmodulithlibrarymanagement.identity.repository.UserRepository;
import com.vasav.springmodulithlibrarymanagement.identity.security.JwtProperties;
import com.vasav.springmodulithlibrarymanagement.identity.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final JwtProperties jwtProperties;
    private final EmailService emailService;

    @Transactional
    public UserResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new EmailAlreadyExistsException("An account with email " + request.email() + " already exists");
        }

        if (userRepository.existsByUsername(request.username())) {
            throw new EmailAlreadyExistsException("Username " + request.username() + " is already taken");
        }

        User user = userMapper.toEntity(request);
        user.setPasswordHash(passwordEncoder.encode(request.password()));
        user.setUserRole(UserRole.MEMBER);
        user.setAccountStatus(AccountStatus.PENDING_PROFILE);

        User savedUser = userRepository.save(user);

        String verificationToken = jwtService.generateEmailVerificationToken(savedUser);
        emailService.sendVerificationEmail(savedUser.getEmail(), verificationToken);

        return userMapper.toResponse(savedUser);
    }

    @Transactional
    public AuthResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        User user = (User) authentication.getPrincipal();
        user.setLastLoginAt(Instant.now());
        userRepository.save(user);

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        return new AuthResponse(
                accessToken,
                refreshToken,
                "Bearer",
                jwtProperties.accessTokenExpiration() / 1000,
                userMapper.toResponse(user)
        );
    }

    @Transactional
    public AuthResponse refresh(RefreshTokenRequest request) {
        String token = request.refreshToken();

        if (!jwtService.isTokenValid(token) || !jwtService.isRefreshToken(token)) {
            throw new InvalidTokenException("Refresh token is invalid or expired");
        }

        Long userId = jwtService.extractUserId(token);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found for id: " + userId));

        String newAccessToken = jwtService.generateAccessToken(user);

        return new AuthResponse(
                newAccessToken,
                token,
                "Bearer",
                jwtProperties.accessTokenExpiration() / 1000,
                userMapper.toResponse(user)
        );
    }

    @Transactional
    public void verifyEmail(String token) {
        if (!jwtService.isTokenValid(token) || !jwtService.isEmailVerificationToken(token)) {
            throw new InvalidTokenException("Verification token is invalid or expired");
        }

        Long userId = jwtService.extractUserId(token);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found for id: " + userId));

        user.setEmailVerifiedAt(Instant.now());
        userRepository.save(user);
    }
}
