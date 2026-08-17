package com.vasav.springmodulithlibrarymanagement.catalog;

import com.vasav.springmodulithlibrarymanagement.catalog.entity.BookCopy;

public interface CatalogOperations {

    BookCopy getCopy(Long copyId);

    BookCopy getAvailableCopy(Long bookId, Long branchId);

    void markLoaned(Long copyId);

    void markReserved(Long copyId);

    void markAvailable(Long copyId, Long branchId);

    record CatalogCopy(
            Long id,
            Long bookId,
            Long branchId
    ) {
    }
}