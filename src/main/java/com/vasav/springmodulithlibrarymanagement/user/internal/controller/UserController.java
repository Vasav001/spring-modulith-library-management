package com.vasav.springmodulithlibrarymanagement.user.internal.controller;

import com.vasav.springmodulithlibrarymanagement.user.api.UserRequest;
import com.vasav.springmodulithlibrarymanagement.user.api.UserResponse;
import com.vasav.springmodulithlibrarymanagement.user.api.UserService;
import com.vasav.springmodulithlibrarymanagement.user.api.UserSummary;
import com.vasav.springmodulithlibrarymanagement.user.internal.entity.UserRole;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    // TODO PROFILE UPDATE ENDPOINTS
    // TODO OPEN API DOCS
    // TODO SECURITY PREAUTHORIZE
    private final UserService userService;

    // summary-get endpoints

    @GetMapping
    public ResponseEntity<Page<UserSummary>> getUsers(Pageable pageable) {
        return ResponseEntity.ok(userService.getAll(pageable));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<UserSummary>> search(@RequestParam String q, Pageable pageable) {
        return ResponseEntity.ok(userService.search(q, pageable));
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<UserSummary> getByUsername(@PathVariable String username) {
        return ResponseEntity.ok(userService.getByUsername(username));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserSummary> getByEmail(@PathVariable String email) {
        return ResponseEntity.ok(userService.getByEmail(email));
    }

    @GetMapping("/{id}/summary")
    public ResponseEntity<UserSummary> getSummary(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getSummary(id));
    }

    // admin-activate-and-role

    @PatchMapping("/{id}/activate")
    public ResponseEntity<Void> activate(@PathVariable Long id) {
        userService.activate(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivate(@PathVariable Long id) {
        userService.deactivate(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/role")
    public ResponseEntity<Void> changeRole(@PathVariable Long id, @RequestParam UserRole role) {
        userService.changeRole(id, role);
        return ResponseEntity.noContent().build();
    }

    // crud

    @PostMapping
    public ResponseEntity<UserResponse> create(@RequestBody @Valid UserRequest request) {
        return ResponseEntity.ok(userService.create(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> update(
            @PathVariable Long id,
            @RequestBody @Valid UserRequest request) {

        return ResponseEntity.ok(userService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}