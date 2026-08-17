package com.vasav.springmodulithlibrarymanagement.circulation.service;

import com.vasav.springmodulithlibrarymanagement.catalog.CatalogOperations;
import com.vasav.springmodulithlibrarymanagement.catalog.event.BookCopyAvailableEvent;
import com.vasav.springmodulithlibrarymanagement.circulation.dto.request.CreateReservationRequest;
import com.vasav.springmodulithlibrarymanagement.circulation.entity.Reservation;
import com.vasav.springmodulithlibrarymanagement.circulation.entity.ReservationStatus;
import com.vasav.springmodulithlibrarymanagement.circulation.event.ReservationCancelledEvent;
import com.vasav.springmodulithlibrarymanagement.circulation.event.ReservationReadyEvent;
import com.vasav.springmodulithlibrarymanagement.circulation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final CatalogOperations catalog;

    private final ApplicationEventPublisher events;

    @Value("${circulation.reservation.hold-duration:2d}")
    private Duration holdDuration;

    @Transactional
    public Reservation create(CreateReservationRequest request, Long userId) {
        if (reservationRepository.existsByBookIdAndUserIdAndStatusIn(
                request.bookId(), userId, List.of(
                        ReservationStatus.PENDING,
                        ReservationStatus.READY
                )
        )) {
            throw new IllegalStateException("You already have an active reservation for this book");
        }

        if (catalog.getAvailableCopy(request.bookId(), request.pickupBranchId()) != null) {
            throw new IllegalStateException("Book is currently available at this branch");
        }

        Reservation reservation = Reservation.builder()
                .bookId(request.bookId())
                .userId(userId)
                .pickupBranchId(request.pickupBranchId())
                .reservationDate(Instant.now())
                .status(ReservationStatus.PENDING)
                .build();

        return reservationRepository.save(reservation);
    }

    //    @ApplicationModuleListener //TODO fix
    @Transactional
    public void onCopyAvailable(BookCopyAvailableEvent event) {
        List<Reservation> reservations =
                reservationRepository.findNextPending(
                        event.bookId(), event.branchId()
                );

        if (reservations.isEmpty()) {
            return;
        }

        Reservation reservation = reservations.getFirst();

        Instant readyUntil = Instant.now().plus(holdDuration);

        reservation.setStatus(ReservationStatus.READY);
        reservation.setReadyCopyId(event.copyId());
        reservation.setReadyUntil(readyUntil);

        catalog.markReserved(event.copyId());

        events.publishEvent(
                new ReservationReadyEvent(
                        reservation.getId(),
                        reservation.getUserId(),
                        reservation.getBookId(),
                        reservation.getPickupBranchId(),
                        readyUntil
                )
        );
    }

    @Transactional
    public void cancel(Long reservationId, Long userId) {
        Reservation reservation =
                reservationRepository.findByIdAndUserId(
                        reservationId, userId
                ).orElseThrow();

        if (reservation.getStatus() == ReservationStatus.CANCELLED ||
                reservation.getStatus() == ReservationStatus.FULFILLED) {
            throw new IllegalStateException("Reservation cannot be cancelled");
        }

        Long copyId = reservation.getReadyCopyId();
        reservation.setStatus(ReservationStatus.CANCELLED);

        events.publishEvent(
                new ReservationCancelledEvent(
                        reservation.getId(),
                        reservation.getUserId(),
                        "MEMBER_CANCELLED"
                )
        );

        if (copyId != null) {
            catalog.markAvailable(copyId, reservation.getPickupBranchId());
        }
    }

    @Scheduled(fixedDelay = 60_000)
    @Transactional
    public void expireReadyReservations() {
        List<Reservation> expired = reservationRepository.findExpiredReady(Instant.now());

        for (Reservation reservation : expired) {
            Long copyId = reservation.getReadyCopyId();
            reservation.setStatus(ReservationStatus.CANCELLED);
            reservation.setReadyCopyId(null);
            reservation.setReadyUntil(null);

            events.publishEvent(
                    new ReservationCancelledEvent(
                            reservation.getId(), reservation.getUserId(), "HOLD_EXPIRED"
                    )
            );

            if (copyId != null) {
                catalog.markAvailable(copyId, reservation.getPickupBranchId());
            }
        }
    }
}