package com.vasav.springmodulithlibrarymanagement.branch.service;

import com.vasav.springmodulithlibrarymanagement.branch.dto.request.BranchCreateRequest;
import com.vasav.springmodulithlibrarymanagement.branch.dto.request.BranchUpdateRequest;
import com.vasav.springmodulithlibrarymanagement.branch.dto.response.BranchResponse;
import com.vasav.springmodulithlibrarymanagement.branch.entity.Branch;
import com.vasav.springmodulithlibrarymanagement.branch.exception.BranchNotFoundException;
import com.vasav.springmodulithlibrarymanagement.branch.mapper.AddressMapper;
import com.vasav.springmodulithlibrarymanagement.branch.mapper.BranchMapper;
import com.vasav.springmodulithlibrarymanagement.branch.repository.BranchRepository;
import com.vasav.springmodulithlibrarymanagement.shared.address.Address;
import com.vasav.springmodulithlibrarymanagement.shared.address.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BranchService {

    private final BranchRepository branchRepository;
    private final BranchMapper branchMapper;
    private final AddressMapper addressMapper;
    private final AddressRepository addressRepository;

    @Transactional
    public BranchResponse createBranch(BranchCreateRequest request) {
        Address address = addressRepository.save(addressMapper.toEntity(request.address()));

        Branch branch = branchMapper.toEntity(request);
        branch.setAddress(address);
        branch.setIsActive(true);

        return branchMapper.toResponse(branchRepository.save(branch));
    }

    @Transactional
    public BranchResponse updateBranch(Long id, BranchUpdateRequest request) {
        Branch branch = branchRepository.findById(id)
                .orElseThrow(() -> new BranchNotFoundException("Branch not found for id: " + id));

        branchMapper.updateEntityFromRequest(request, branch);

        if (request.address() != null) {
            Address address = branch.getAddress();
            addressMapper.updateEntityFromRequest(request.address(), address);
            addressRepository.save(address);
        }

        return branchMapper.toResponse(branchRepository.save(branch));
    }

    @Transactional(readOnly = true)
    public BranchResponse getBranchById(Long id) {
        Branch branch = branchRepository.findById(id)
                .orElseThrow(() -> new BranchNotFoundException("Branch not found for id: " + id));
        return branchMapper.toResponse(branch);
    }

    @Transactional(readOnly = true)
    public List<BranchResponse> getActiveBranches() {
        return branchRepository.findAllByIsActiveTrueOrderByNameAsc().stream()
                .map(branchMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<BranchResponse> getAllBranches() {
        return branchRepository.findAll().stream()
                .map(branchMapper::toResponse)
                .toList();
    }

    @Transactional
    public BranchResponse setActive(Long id, boolean active) {
        Branch branch = branchRepository.findById(id)
                .orElseThrow(() -> new BranchNotFoundException("Branch not found for id: " + id));

        branch.setIsActive(active);
        return branchMapper.toResponse(branchRepository.save(branch));
    }
}
