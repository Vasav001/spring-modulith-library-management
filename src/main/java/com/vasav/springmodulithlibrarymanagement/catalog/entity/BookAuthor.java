package com.vasav.springmodulithlibrarymanagement.catalog.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "book_authors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookAuthor {

    @EmbeddedId
    private BookAuthorId id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("bookId")
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("authorId")
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;

    @Column(name = "author_order", nullable = false)
    private Short authorOrder = 0;
}