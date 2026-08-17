package com.vasav.springmodulithlibrarymanagement.circulation.repository;

import com.vasav.springmodulithlibrarymanagement.circulation.entity.Reservation;
import com.vasav.springmodulithlibrarymanagement.circulation.entity.ReservationStatus;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository
        extends JpaRepository<Reservation, Long> {

    boolean existsByBookIdAndUserIdAndStatusIn(
            Long bookId,
            Long userId,
            List<ReservationStatus> statuses
    );

    boolean existsByBookIdAndPickupBranchIdAndStatus(
            Long bookId,
            Long pickupBranchId,
            ReservationStatus status
    );

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
                select r
                from Reservation r
                where r.bookId = :bookId
                  and r.pickupBranchId = :branchId
                  and r.status = PENDING
                order by r.reservationDate
            """)
    List<Reservation> findNextPending(
            @Param("bookId") Long bookId,
            @Param("branchId") Long branchId
    );

    @Query("""
                select r
                from Reservation r
                where r.status = READY
                  and r.readyUntil <= :now
            """)
    List<Reservation> findExpiredReady(@Param("now") Instant now);

    Optional<Reservation> findByIdAndUserId(Long id, Long userId);

    Optional<Reservation> findByUserIdAndBookIdAndStatus(Long userId, Long bookId, ReservationStatus status);
}