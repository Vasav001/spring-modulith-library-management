package com.vasav.springmodulithlibrarymanagement.catalog.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(name = "book_copies")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookCopy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @Column(name = "branch_id", nullable = false)
    private Long branchId;

    @Column(unique = true, length = 50)
    private String barcode;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private BookCopyStatus status = BookCopyStatus.AVAILABLE;

    @Enumerated(EnumType.STRING)
    @Column(name = "physical_condition", length = 20)
    @Builder.Default
    private PhysicalCondition physicalCondition = PhysicalCondition.GOOD;

    @Column(name = "acquisition_date")
    private LocalDate acquisitionDate;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;
}