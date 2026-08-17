package com.vasav.springmodulithlibrarymanagement.branch.controller;

import com.vasav.springmodulithlibrarymanagement.branch.dto.request.BranchCreateRequest;
import com.vasav.springmodulithlibrarymanagement.branch.dto.request.BranchUpdateRequest;
import com.vasav.springmodulithlibrarymanagement.branch.dto.response.BranchResponse;
import com.vasav.springmodulithlibrarymanagement.branch.service.BranchService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/branches")
@RequiredArgsConstructor
public class BranchController {

    private final BranchService branchService;

    @GetMapping
    public List<BranchResponse> getActiveBranches() {
        return branchService.getActiveBranches();
    }

    @GetMapping("/{id}")
    public BranchResponse getBranchById(@PathVariable Long id) {
        return branchService.getBranchById(id);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('LIBRARIAN', 'ADMIN')")
    public List<BranchResponse> getAllBranches() {
        return branchService.getAllBranches();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public BranchResponse createBranch(@Valid @RequestBody BranchCreateRequest request) {
        return branchService.createBranch(request);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public BranchResponse updateBranch(@PathVariable Long id, @Valid @RequestBody BranchUpdateRequest request) {
        return branchService.updateBranch(id, request);
    }

    @PatchMapping("/{id}/deactivate")
    @PreAuthorize("hasRole('ADMIN')")
    public BranchResponse deactivateBranch(@PathVariable Long id) {
        return branchService.setActive(id, false);
    }

    @PatchMapping("/{id}/activate")
    @PreAuthorize("hasRole('ADMIN')")
    public BranchResponse activateBranch(@PathVariable Long id) {
        return branchService.setActive(id, true);
    }
}
