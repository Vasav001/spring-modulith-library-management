ALTER TABLE book_copies
    RENAME COLUMN condition TO physical_condition;

ALTER TABLE loans
    ADD CONSTRAINT chk_loans_due_date
        CHECK (due_date >= loan_date::DATE);

ALTER TABLE loans
    ADD CONSTRAINT chk_loans_return_date
        CHECK (
            return_date IS NULL
                OR return_date >= loan_date::DATE
            );

CREATE UNIQUE INDEX idx_loans_active_book_copy
    ON loans (book_copy_id)
    WHERE status = 'ACTIVE';

ALTER TABLE books
    ALTER COLUMN isbn TYPE VARCHAR(20);