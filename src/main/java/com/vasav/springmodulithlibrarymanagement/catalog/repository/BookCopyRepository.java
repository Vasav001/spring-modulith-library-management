package com.vasav.springmodulithlibrarymanagement.catalog.repository;

import com.vasav.springmodulithlibrarymanagement.catalog.entity.BookCopy;
import com.vasav.springmodulithlibrarymanagement.catalog.entity.BookCopyStatus;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookCopyRepository extends JpaRepository<BookCopy, Long> {

    List<BookCopy> findAllByBookId(Long bookId);

    List<BookCopy> findAllByBranchId(Long branchId);

    List<BookCopy> findAllByBranchIdAndStatus(Long branchId, BookCopyStatus status);

    boolean existsByBarcode(String barcode);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
                select c
                from BookCopy c
                where c.book.id = :bookId
                  and c.branchId = :branchId
                  and c.status = com.vasav.springmodulithlibrarymanagement.catalog.entity.BookCopyStatus.AVAILABLE
                order by c.id
            """)
    List<BookCopy> findAvailableCopiesForUpdate(
            @Param("bookId") Long bookId,
            @Param("branchId") Long branchId,
            Pageable pageable
    );
}