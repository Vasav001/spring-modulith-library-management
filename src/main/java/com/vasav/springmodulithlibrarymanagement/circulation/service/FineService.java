package com.vasav.springmodulithlibrarymanagement.circulation.service;

import com.vasav.springmodulithlibrarymanagement.circulation.entity.Fine;
import com.vasav.springmodulithlibrarymanagement.circulation.entity.FineReason;
import com.vasav.springmodulithlibrarymanagement.circulation.entity.Loan;
import com.vasav.springmodulithlibrarymanagement.circulation.entity.LoanStatus;
import com.vasav.springmodulithlibrarymanagement.circulation.repository.FineRepository;
import com.vasav.springmodulithlibrarymanagement.circulation.repository.LoanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FineService {

    private final LoanRepository loanRepository;
    private final FineRepository fineRepository;

    @Value("${circulation.fine.overdue-amount:50}")
    private BigDecimal overdueAmount;

    @Scheduled(fixedDelay = 3_600_000)
    @Transactional
    public void createOverdueFines() {
        List<Loan> overdue = loanRepository.findByStatusAndDueDateBefore(
                LoanStatus.ACTIVE, Instant.now()
        );

        for (Loan loan : overdue) {
            if (fineRepository.existsByLoanIdAndReason(loan.getId(), FineReason.OVERDUE)) {
                continue;
            }

            fineRepository.save(
                    Fine.builder()
                            .loanId(loan.getId())
                            .amount(overdueAmount)
                            .reason(FineReason.OVERDUE)
                            .build()
            );
        }
    }
}