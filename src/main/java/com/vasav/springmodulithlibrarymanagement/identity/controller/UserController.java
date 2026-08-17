package com.vasav.springmodulithlibrarymanagement.identity.controller;

import com.vasav.springmodulithlibrarymanagement.identity.dto.request.UpdateProfileRequest;
import com.vasav.springmodulithlibrarymanagement.identity.dto.response.UserResponse;
import com.vasav.springmodulithlibrarymanagement.identity.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public UserResponse getCurrentUser() {
        return userService.getCurrentUserProfile();
    }

    @PutMapping("/me")
    public UserResponse updateCurrentUser(@Valid @RequestBody UpdateProfileRequest request) {
        return userService.updateCurrentUserProfile(request);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('LIBRARIAN', 'ADMIN')")
    public UserResponse getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }
}
