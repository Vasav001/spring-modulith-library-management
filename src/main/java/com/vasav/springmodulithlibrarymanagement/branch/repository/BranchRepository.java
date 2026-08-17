package com.vasav.springmodulithlibrarymanagement.branch.repository;

import com.vasav.springmodulithlibrarymanagement.branch.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BranchRepository extends JpaRepository<Branch, Long> {
    List<Branch> findAllByIsActiveTrueOrderByNameAsc();

}