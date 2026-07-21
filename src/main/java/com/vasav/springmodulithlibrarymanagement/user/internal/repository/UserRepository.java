package com.vasav.springmodulithlibrarymanagement.user.internal.repository;

import com.vasav.springmodulithlibrarymanagement.user.internal.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}