package com.vasav.springmodulithlibrarymanagement.user.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    Page<UserSummary> getAll(Pageable pageable);

    Page<UserSummary> search(String query, Pageable pageable);

    UserSummary getByUsername(String username);

    UserSummary getByEmail(String email);

    UserSummary getSummary(Long id);

    void activate(Long id);

    void deactivate(Long id);

    void changeRole(Long id, UserRole role);
    
    UserResponse create(UserRequest request);

    UserResponse getById(Long id);

    UserResponse update(Long id, UserRequest request);

    void delete(Long id);
}
