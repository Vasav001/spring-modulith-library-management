package com.vasav.springmodulithlibrarymanagement.circulation.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Table(name = "loans")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "book_copy_id", nullable = false)
    private Long bookCopyId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "borrow_branch_id", nullable = false)
    private Long borrowBranchId;

    @Column(name = "return_branch_id")
    private Long returnBranchId;

    @Column(name = "issued_by", nullable = false)
    private Long issuedBy;

    @Column(name = "returned_by")
    private Long returnedBy;

    @Column(name = "loan_date", nullable = false)
    private Instant loanDate;

    @Column(name = "due_date", nullable = false)
    private Instant dueDate;

    @Column(name = "return_date")
    private Instant returnDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private LoanStatus status = LoanStatus.ACTIVE;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;
}