CREATE TABLE refresh_tokens
(
    id            BIGINT PRIMARY KEY,
    user_id       BIGINT NOT NULL,
    token_hash    VARCHAR(255) NOT NULL UNIQUE, -- store hash, never the raw token
    issued_at     TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    expires_at    TIMESTAMP NOT NULL,
    revoked_at    TIMESTAMP,
    replaced_by   BIGINT, -- token rotation chain
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (replaced_by) REFERENCES refresh_tokens (id) ON DELETE SET NULL
);

CREATE INDEX idx_refresh_tokens_user_id ON refresh_tokens (user_id);
CREATE INDEX idx_refresh_tokens_expiry ON refresh_tokens (expires_at) WHERE revoked_at IS NULL;

ALTER TABLE reservations
    ADD COLUMN queue_position INT;

CREATE UNIQUE INDEX idx_reservations_queue_order
    ON reservations (book_id, queue_position)
    WHERE status IN ('PENDING', 'READY');


CREATE TYPE notification_type AS ENUM (
    'RESERVATION_READY', 'LOAN_DUE_SOON', 'LOAN_OVERDUE', 'FINE_ISSUED'
    );

CREATE TABLE notifications
(
    id                 BIGINT PRIMARY KEY,
    user_id            BIGINT NOT NULL,
    notification_type  notification_type NOT NULL,
    reference_id       BIGINT, -- loan_id / reservation_id / fine_id depending on type; no FK by design
    message            VARCHAR(500) NOT NULL,
    is_read            BOOLEAN DEFAULT false,
    sent_at            TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    read_at            TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE INDEX idx_notifications_user_unread ON notifications (user_id) WHERE is_read = false;
COMMENT ON COLUMN notifications.reference_id IS
    'Polymorphic reference to loans/reservations/fines depending on notification_type; intentionally no FK constraint';

CREATE TYPE payment_method AS ENUM ('CASH', 'CARD', 'ONLINE', 'WAIVED');

CREATE TABLE fine_payments
(
    id              BIGINT PRIMARY KEY,
    fine_id         BIGINT NOT NULL,
    amount_paid     DECIMAL(10, 2) NOT NULL CHECK (amount_paid > 0),
    payment_method  payment_method NOT NULL,
    transaction_ref VARCHAR(100),
    paid_at         TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    processed_by    BIGINT,
    FOREIGN KEY (fine_id) REFERENCES fines (id) ON DELETE CASCADE,
    FOREIGN KEY (processed_by) REFERENCES users (id) ON DELETE SET NULL
);

CREATE INDEX idx_fine_payments_fine_id ON fine_payments (fine_id);