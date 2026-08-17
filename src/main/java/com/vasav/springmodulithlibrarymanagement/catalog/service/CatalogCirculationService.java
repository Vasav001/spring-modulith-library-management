package com.vasav.springmodulithlibrarymanagement.catalog.service;

import com.vasav.springmodulithlibrarymanagement.catalog.CatalogOperations;
import com.vasav.springmodulithlibrarymanagement.catalog.entity.BookCopy;
import com.vasav.springmodulithlibrarymanagement.catalog.entity.BookCopyStatus;
import com.vasav.springmodulithlibrarymanagement.catalog.event.BookCopyAvailableEvent;
import com.vasav.springmodulithlibrarymanagement.catalog.exception.BookCopyNotFoundException;
import com.vasav.springmodulithlibrarymanagement.catalog.repository.BookCopyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CatalogCirculationService implements CatalogOperations {

    private final BookCopyRepository bookCopyRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public BookCopy getAvailableCopy(Long bookId, Long branchId) {
        List<BookCopy> copies = bookCopyRepository.findAvailableCopiesForUpdate(
                bookId, branchId, PageRequest.of(0, 1)
        );

        return copies.isEmpty() ? null : copies.getFirst();
    }

    @Transactional
    public void markLoaned(Long copyId) {
        BookCopy copy = bookCopyRepository.findById(copyId).orElseThrow();
        copy.setStatus(BookCopyStatus.LOANED);
    }

    @Transactional
    public void markReserved(Long copyId) {
        BookCopy copy = bookCopyRepository.findById(copyId).orElseThrow();
        copy.setStatus(BookCopyStatus.RESERVED);
    }

    @Transactional
    public void markAvailable(Long copyId, Long branchId) {
        BookCopy copy = bookCopyRepository.findById(copyId).orElseThrow();
        copy.setBranchId(branchId);
        copy.setStatus(BookCopyStatus.AVAILABLE);
        eventPublisher.publishEvent(
                new BookCopyAvailableEvent(
                        copy.getId(), copy.getBook().getId(), branchId
                )
        );
    }

    @Transactional(readOnly = true)
    public BookCopy getCopy(Long copyId) {
        return bookCopyRepository.findById(copyId).orElseThrow(() -> new BookCopyNotFoundException(
                        "Book copy not found: " + copyId
                )
        );
    }
}