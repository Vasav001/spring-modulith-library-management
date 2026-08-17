DROP INDEX IF EXISTS idx_reservations_unique_pending;

CREATE UNIQUE INDEX idx_reservations_one_active_per_user_book
    ON reservations (book_id, user_id)
    WHERE status IN ('PENDING', 'READY');

CREATE UNIQUE INDEX idx_fines_one_overdue_per_loan
    ON fines (loan_id)
    WHERE reason = 'OVERDUE';