### spring-modulith-library-management

# Library Management System

A backend application for managing books, physical book copies, library branches, members, loans, reservations, and fines.

Built as a **modular monolith with Spring Modulith**, with business capabilities separated into independent application modules.

## Tech Stack

* **Java 21**
* **Spring Boot 4**
* **Spring Modulith**
* **Spring Security**
* **JWT**
* **Spring Data JPA / Hibernate**
* **PostgreSQL**
* **Flyway**
* **MapStruct**
* **Lombok**
* **Maven**

## Architecture

The application follows a **modular monolith** architecture.

Instead of organizing the entire codebase around technical layers such as `controller`, `service`, and `repository`, the system is primarily divided by **business responsibility**.

Each module owns its domain logic and persistence concerns, while module boundaries make dependencies explicit.

### Modules

#### `identity`

Responsible for users and authentication.

* User registration and account management
* Authentication
* JWT access and refresh tokens
* Email verification
* Role-based authorization
* User roles such as `MEMBER`, `LIBRARIAN`, and `ADMIN`

This module owns identity-related concerns and provides the security foundation for the rest of the application.

#### `catalog`

Responsible for the library's book catalog and inventory.

* Books
* Authors
* Publishers
* Categories
* Physical book copies
* Book availability
* Catalog relationships

A distinction is maintained between a **book** and a **physical copy** of that book. This allows the system to track individual copies independently while still maintaining a shared bibliographic record.

#### `circulation`

Responsible for the actual borrowing lifecycle.

* Loan creation
* Returning books
* Loan status management
* Reservations
* Reservation status
* Fine calculation and management

This module contains the core rules around how members interact with physical book copies.

#### `branch`

Responsible for physical library locations.

* Library branches
* Branch information
* Branch-specific inventory relationships
* Managing where physical copies belong

Keeping branch management separate from catalog and circulation allows location-specific concerns to remain isolated.

#### `shared`

Contains concepts that are intentionally shared between modules.

The shared module is kept small to avoid turning it into a general-purpose dependency that every module relies on.

## Domain Model

The system separates several concepts that are often incorrectly modeled as a single entity.

For example:

```text
Book
 └── Physical Copies
       ├── Copy A → Available
       ├── Copy B → Borrowed
       └── Copy C → Reserved
```

A `Book` represents the bibliographic item, while a `BookCopy` represents an individual physical copy that can be borrowed, reserved, or assigned to a branch.

This allows circulation operations to work with real inventory rather than only book-level information.

## Security

Authentication and authorization are implemented using **Spring Security and JWT**.

The application supports:

* Stateless authentication
* Access tokens
* Refresh tokens
* Role-based access control
* Method-level authorization

Authorization rules are applied at the application layer to restrict operations based on user roles.

## Persistence

The application uses **Spring Data JPA with Hibernate** and PostgreSQL.

Database changes are managed through **Flyway migrations**, keeping schema evolution version-controlled and reproducible.

Persistence responsibilities remain inside their respective business modules rather than exposing repositories across the entire application.

## API Design

The API follows a REST-oriented structure with DTOs separating the external API contract from persistence entities.

Examples of API areas include:

```text
/auth
/users
/books
/book-copies
/authors
/categories
/publishers
/branches
/loans
/reservations
/fines
```

Request validation and centralized exception handling are used to keep controllers focused on HTTP concerns while business rules remain within the appropriate module.

## Mapping

**MapStruct** is used for mapping between DTOs and domain/persistence objects.

This keeps mapping logic explicit and avoids coupling API representations directly to database entities.

## Why Modular Monolith?

The project intentionally uses a modular monolith rather than immediately splitting the system into microservices.

This provides:

* Clear business boundaries
* Independent module ownership
* Reduced coupling
* Easier local development
* A single deployable application
* Simpler transactions and persistence
* A structure that can evolve toward distributed services if required

The goal is to establish **good boundaries first**, rather than introducing distributed-system complexity prematurely.

## Engineering Focus

The project focuses on applying backend engineering principles to a realistic domain:

* Domain-oriented modularization
* Explicit module boundaries
* Separation of API, business, and persistence concerns
* Transactional service operations
* Role-based security
* Relational data modeling
* Database migrations
* DTO-based API contracts
* Validation and exception handling
* Maintainable package structure

## Project Goal

The primary goal of the project is to demonstrate how a non-trivial backend can be structured around **business capabilities and well-defined module boundaries** while remaining a single, maintainable application.

---
