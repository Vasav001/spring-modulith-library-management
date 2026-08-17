package com.vasav.springmodulithlibrarymanagement.identity.security;

import com.vasav.springmodulithlibrarymanagement.identity.entity.User;
import com.vasav.springmodulithlibrarymanagement.identity.entity.UserRole;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public final class SecurityUtil {

    private SecurityUtil() {
    }

    public static User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()
                || !(authentication.getPrincipal() instanceof User user)) {
            throw new AuthenticationCredentialsNotFoundException("No authenticated user found in security context");
        }

        return user;
    }

    public static Optional<User> getCurrentUserOrEmpty() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof User user)) {
            return Optional.empty();
        }

        return Optional.of(user);
    }

    public static Long getCurrentUserId() {
        return getCurrentUser().getId();
    }

    public static String getCurrentUserEmail() {
        return getCurrentUser().getEmail();
    }

    public static boolean hasRole(UserRole role) {
        return getCurrentUser().getUserRole() == role;
    }
}
