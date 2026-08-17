ALTER TABLE reservations
    ADD COLUMN ready_copy_id BIGINT;

ALTER TABLE reservations
    ADD CONSTRAINT fk_reservation_ready_copy
        FOREIGN KEY (ready_copy_id)
            REFERENCES book_copies (id)
            ON DELETE RESTRICT;

CREATE UNIQUE INDEX idx_reservations_ready_copy
    ON reservations (ready_copy_id)
    WHERE ready_copy_id IS NOT NULL;