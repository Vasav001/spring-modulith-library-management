package com.vasav.springmodulithlibrarymanagement.circulation.repository;

import com.vasav.springmodulithlibrarymanagement.circulation.entity.Fine;
import com.vasav.springmodulithlibrarymanagement.circulation.entity.FineReason;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FineRepository extends JpaRepository<Fine, Long> {

    boolean existsByLoanIdAndReason(Long loanId, FineReason reason);
}