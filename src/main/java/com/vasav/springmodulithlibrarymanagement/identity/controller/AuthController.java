package com.vasav.springmodulithlibrarymanagement.identity.controller;

import com.vasav.springmodulithlibrarymanagement.identity.dto.request.LoginRequest;
import com.vasav.springmodulithlibrarymanagement.identity.dto.request.RefreshTokenRequest;
import com.vasav.springmodulithlibrarymanagement.identity.dto.request.RegisterRequest;
import com.vasav.springmodulithlibrarymanagement.identity.dto.response.AuthResponse;
import com.vasav.springmodulithlibrarymanagement.identity.dto.response.UserResponse;
import com.vasav.springmodulithlibrarymanagement.identity.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse register(@Valid @RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @PostMapping("/refresh")
    public AuthResponse refresh(@Valid @RequestBody RefreshTokenRequest request) {
        return authService.refresh(request);
    }

    @GetMapping("/verify-email")
    public ResponseEntity<Void> verifyEmail(@RequestParam String token) {
        authService.verifyEmail(token);
        return ResponseEntity.ok().build();
    }
}
