CREATE TYPE user_role AS ENUM ('MEMBER', 'LIBRARIAN', 'ADMIN');
CREATE TYPE book_copy_status AS ENUM ('AVAILABLE', 'LOANED', 'RESERVED', 'DAMAGED', 'LOST');
CREATE TYPE loan_status AS ENUM ('ACTIVE', 'RETURNED', 'OVERDUE');
CREATE TYPE fine_reason AS ENUM ('OVERDUE', 'DAMAGE', 'LOSS', 'OTHER');
CREATE TYPE reservation_status AS ENUM ('PENDING', 'READY', 'FULFILLED', 'CANCELLED');


CREATE TABLE addresses
(
    id             BIGINT PRIMARY KEY,
    street         VARCHAR(255) NOT NULL,
    city           VARCHAR(100) NOT NULL,
    state_province VARCHAR(100),
    postal_code    VARCHAR(20),
    country        VARCHAR(100) DEFAULT 'India',
    created_at     TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    updated_at     TIMESTAMP    DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_addresses_city_state ON addresses (city, state_province);


CREATE TABLE branches
(
    id            BIGINT PRIMARY KEY,
    name          VARCHAR(150) NOT NULL,
    address_id    BIGINT       NOT NULL,
    phone         VARCHAR(20),
    email         VARCHAR(100),
    opening_hours VARCHAR(100),
    is_active     BOOLEAN   DEFAULT true,
    created_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (address_id) REFERENCES addresses (id) ON DELETE RESTRICT
);

CREATE INDEX idx_branches_is_active ON branches (is_active);


CREATE TABLE users
(
    id                  BIGINT PRIMARY KEY,
    username            VARCHAR(50) UNIQUE  NOT NULL,
    email               VARCHAR(100) UNIQUE NOT NULL,
    password_hash       VARCHAR(255)        NOT NULL,
    first_name          VARCHAR(100)        NOT NULL,
    last_name           VARCHAR(100),
    phone               VARCHAR(20),
    address_id          BIGINT              NOT NULL,
    preferred_branch_id BIGINT,
    user_role           user_role           NOT NULL DEFAULT 'MEMBER',
    is_active           BOOLEAN                      DEFAULT true,
    created_at          TIMESTAMP                    DEFAULT CURRENT_TIMESTAMP,
    updated_at          TIMESTAMP                    DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (address_id) REFERENCES addresses (id) ON DELETE RESTRICT,
    FOREIGN KEY (preferred_branch_id) REFERENCES branches (id) ON DELETE SET NULL
);

CREATE INDEX idx_users_username ON users (username);
CREATE INDEX idx_users_email ON users (email);
CREATE INDEX idx_users_user_role ON users (user_role);
CREATE INDEX idx_users_is_active ON users (is_active);


CREATE TABLE publishers
(
    id         BIGINT PRIMARY KEY,
    name       VARCHAR(200) NOT NULL UNIQUE,
    address_id BIGINT,
    phone      VARCHAR(20),
    email      VARCHAR(100),
    country    VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (address_id) REFERENCES addresses (id) ON DELETE SET NULL
);

CREATE INDEX idx_publishers_name ON publishers (name);


CREATE TABLE authors
(
    id            BIGINT PRIMARY KEY,
    name          VARCHAR(200) NOT NULL,
    biography     TEXT,
    date_of_birth DATE,
    nationality   VARCHAR(100),
    created_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_authors_name ON authors (name);


CREATE TABLE categories
(
    id          BIGINT PRIMARY KEY,
    name        VARCHAR(100) NOT NULL UNIQUE,
    description VARCHAR(500),
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_categories_name ON categories (name);


CREATE TABLE books
(
    id               BIGINT PRIMARY KEY,
    title            VARCHAR(300) NOT NULL,
    isbn             VARCHAR(13) UNIQUE,
    category_id      BIGINT       NOT NULL,
    publisher_id     BIGINT,
    publication_year SMALLINT,
    description      TEXT,
    language         VARCHAR(50) DEFAULT 'English',
    pages            INT,
    is_active        BOOLEAN     DEFAULT true,
    created_at       TIMESTAMP   DEFAULT CURRENT_TIMESTAMP,
    updated_at       TIMESTAMP   DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (category_id) REFERENCES categories (id) ON DELETE RESTRICT,
    FOREIGN KEY (publisher_id) REFERENCES publishers (id) ON DELETE SET NULL
);

CREATE INDEX idx_books_title ON books (title);
CREATE INDEX idx_books_isbn ON books (isbn);
CREATE INDEX idx_books_category_id ON books (category_id);
CREATE INDEX idx_books_is_active ON books (is_active);


CREATE TABLE book_authors
(
    book_id      BIGINT NOT NULL,
    author_id    BIGINT NOT NULL,
    author_order INT    NOT NULL DEFAULT 0,
    PRIMARY KEY (book_id, author_id),
    FOREIGN KEY (book_id) REFERENCES books (id) ON DELETE CASCADE,
    FOREIGN KEY (author_id) REFERENCES authors (id) ON DELETE RESTRICT
);

CREATE INDEX idx_book_authors_author_id ON book_authors (author_id);


CREATE TABLE book_copies
(
    id               BIGINT PRIMARY KEY,
    book_id          BIGINT           NOT NULL,
    branch_id        BIGINT           NOT NULL,
    copy_number      INT              NOT NULL,
    barcode          VARCHAR(50) UNIQUE,
    status           book_copy_status NOT NULL DEFAULT 'AVAILABLE',
    condition        VARCHAR(30)               DEFAULT 'GOOD',
    acquisition_date DATE,
    created_at       TIMESTAMP                 DEFAULT CURRENT_TIMESTAMP,
    updated_at       TIMESTAMP                 DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (book_id, branch_id, copy_number),
    FOREIGN KEY (book_id) REFERENCES books (id) ON DELETE CASCADE,
    FOREIGN KEY (branch_id) REFERENCES branches (id) ON DELETE CASCADE
);

CREATE INDEX idx_book_copies_branch_id ON book_copies (branch_id);
CREATE INDEX idx_book_copies_book_id ON book_copies (book_id);


CREATE TABLE loans
(
    id                 BIGINT PRIMARY KEY,
    book_copy_id       BIGINT      NOT NULL,
    user_id            BIGINT      NOT NULL,
    borrowed_branch_id BIGINT      NOT NULL,
    loan_date          TIMESTAMP            DEFAULT CURRENT_TIMESTAMP,
    due_date           DATE        NOT NULL,
    return_date        DATE,
    return_branch_id   BIGINT,
    status             loan_status NOT NULL DEFAULT 'ACTIVE',
    created_at         TIMESTAMP            DEFAULT CURRENT_TIMESTAMP,
    updated_at         TIMESTAMP            DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (book_copy_id) REFERENCES book_copies (id) ON DELETE RESTRICT,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE RESTRICT,
    FOREIGN KEY (borrowed_branch_id) REFERENCES branches (id) ON DELETE RESTRICT,
    FOREIGN KEY (return_branch_id) REFERENCES branches (id) ON DELETE SET NULL
);

CREATE INDEX idx_loans_user_id ON loans (user_id);
CREATE INDEX idx_loans_status ON loans (status);
CREATE INDEX idx_loans_due_date ON loans (due_date);
CREATE INDEX idx_loans_book_copy_id ON loans (book_copy_id);
CREATE INDEX idx_loans_return_date ON loans (return_date);


CREATE TABLE reservations
(
    id                  BIGINT PRIMARY KEY,
    book_id             BIGINT             NOT NULL,
    user_id             BIGINT             NOT NULL,
    preferred_branch_id BIGINT,
    reservation_date    TIMESTAMP                   DEFAULT CURRENT_TIMESTAMP,
    status              reservation_status NOT NULL DEFAULT 'PENDING',
    ready_date          TIMESTAMP,
    fulfilled_date      TIMESTAMP,
    created_at          TIMESTAMP                   DEFAULT CURRENT_TIMESTAMP,
    updated_at          TIMESTAMP                   DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (book_id) REFERENCES books (id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (preferred_branch_id) REFERENCES branches (id) ON DELETE SET NULL
);

CREATE INDEX idx_reservations_user_id ON reservations (user_id);
CREATE INDEX idx_reservations_book_id ON reservations (book_id);
CREATE INDEX idx_reservations_status ON reservations (status);


CREATE TABLE fines
(
    id                 BIGINT PRIMARY KEY,
    loan_id            BIGINT         NOT NULL,
    amount             DECIMAL(10, 2) NOT NULL CHECK (amount >= 0),
    reason             fine_reason    NOT NULL,
    reason_description VARCHAR(255),
    is_paid            BOOLEAN   DEFAULT false,
    paid_date          TIMESTAMP,
    created_at         TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at         TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (loan_id) REFERENCES loans (id) ON DELETE CASCADE
);

CREATE INDEX idx_fines_loan_id ON fines (loan_id);
CREATE INDEX idx_fines_is_paid ON fines (is_paid);