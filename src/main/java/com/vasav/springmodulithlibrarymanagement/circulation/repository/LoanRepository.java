package com.vasav.springmodulithlibrarymanagement.circulation.repository;

import com.vasav.springmodulithlibrarymanagement.circulation.entity.Loan;
import com.vasav.springmodulithlibrarymanagement.circulation.entity.LoanStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long> {

    boolean existsByUserIdAndBookCopyIdAndStatus(Long userId, Long bookCopyId, LoanStatus status);

    List<Loan> findByStatusAndDueDateBefore(LoanStatus status, Instant date);
}