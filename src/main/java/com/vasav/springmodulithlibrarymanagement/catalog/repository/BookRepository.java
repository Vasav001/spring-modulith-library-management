package com.vasav.springmodulithlibrarymanagement.catalog.repository;

import com.vasav.springmodulithlibrarymanagement.catalog.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    boolean existsByIsbn(String isbn);

    List<Book> findAllByActiveTrueOrderByTitleAsc();
}