package com.vasav.springmodulithlibrarymanagement.circulation.service;

import com.vasav.springmodulithlibrarymanagement.catalog.CatalogOperations;
import com.vasav.springmodulithlibrarymanagement.circulation.dto.request.IssueLoanRequest;
import com.vasav.springmodulithlibrarymanagement.circulation.dto.request.ReturnLoanRequest;
import com.vasav.springmodulithlibrarymanagement.circulation.entity.Loan;
import com.vasav.springmodulithlibrarymanagement.circulation.entity.LoanStatus;
import com.vasav.springmodulithlibrarymanagement.circulation.entity.Reservation;
import com.vasav.springmodulithlibrarymanagement.circulation.entity.ReservationStatus;
import com.vasav.springmodulithlibrarymanagement.circulation.event.LoanIssuedEvent;
import com.vasav.springmodulithlibrarymanagement.circulation.event.LoanReturnedEvent;
import com.vasav.springmodulithlibrarymanagement.circulation.repository.LoanRepository;
import com.vasav.springmodulithlibrarymanagement.circulation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class LoanService {

    private final LoanRepository loanRepository;
    private final ReservationRepository reservationRepository;
    private final CatalogOperations catalog;

    private final ApplicationEventPublisher events;

    @Transactional
    public Loan issue(IssueLoanRequest request, Long userId, Long issuedBy) {
        Reservation ready = reservationRepository.findByUserIdAndBookIdAndStatus(
                        userId,
                        request.bookId(),
                        ReservationStatus.READY
                )
                .orElse(null);

        Long copyId;

        if (ready != null) {
            copyId = ready.getReadyCopyId();
            ready.setStatus(ReservationStatus.FULFILLED);

        } else {
            if (reservationRepository.existsByBookIdAndPickupBranchIdAndStatus(
                    request.bookId(), request.borrowBranchId(), ReservationStatus.PENDING
            )) {
                throw new IllegalStateException(
                        "This book has pending reservations"
                );
            }

            //TODO FIX THIS; TRY TO REMOVE BOOKCPPY
//
//            BookCopy copy = catalog.getAvailableCopy(
//                    request.bookId(),
//                    request.borrowBranchId()
//            );
//
//            if (copy == null) {
//                throw new IllegalStateException("No copy available");
//            }
        }

//        catalog.markLoaned(copyId);
        catalog.markLoaned(null);

        Loan loan = Loan.builder()
//                .bookCopyId(copyId)
                .bookCopyId(null)
                .userId(userId)
                .borrowBranchId(request.borrowBranchId())
                .issuedBy(issuedBy)
                .loanDate(Instant.now())
                .dueDate(request.dueDate())
                .status(LoanStatus.ACTIVE)
                .build();

        loan = loanRepository.save(loan);

        events.publishEvent(
                new LoanIssuedEvent(
                        loan.getId(),
                        userId,
                        null,
//                        copyId,
                        request.borrowBranchId(),
                        request.dueDate()
                )
        );

        return loan;
    }

    @Transactional
    public Loan returnLoan(Long loanId, ReturnLoanRequest request) {
        Loan loan = loanRepository.findById(loanId).orElseThrow();

        if (loan.getStatus() != LoanStatus.ACTIVE) {
            throw new IllegalStateException("Loan already returned");
        }

        loan.setStatus(LoanStatus.RETURNED);
        loan.setReturnDate(Instant.now());
        loan.setReturnBranchId(request.returnBranchId());
        loan.setReturnedBy(request.returnedBy());

        catalog.markAvailable(loan.getBookCopyId(), request.returnBranchId());

        events.publishEvent(
                new LoanReturnedEvent(
                        loan.getId(),
                        loan.getUserId(),
                        loan.getBookCopyId(),
                        loan.getBorrowBranchId(),
                        request.returnBranchId(),
                        loan.getDueDate(),
                        loan.getReturnDate()
                )
        );

        return loan;
    }
}