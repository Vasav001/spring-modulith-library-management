package com.vasav.springmodulithlibrarymanagement.catalog.service;

import com.vasav.springmodulithlibrarymanagement.catalog.dto.request.BookCreateRequest;
import com.vasav.springmodulithlibrarymanagement.catalog.dto.request.BookUpdateRequest;
import com.vasav.springmodulithlibrarymanagement.catalog.dto.response.BookResponse;
import com.vasav.springmodulithlibrarymanagement.catalog.entity.Book;
import com.vasav.springmodulithlibrarymanagement.catalog.entity.BookAuthor;
import com.vasav.springmodulithlibrarymanagement.catalog.entity.BookAuthorId;
import com.vasav.springmodulithlibrarymanagement.catalog.exception.*;
import com.vasav.springmodulithlibrarymanagement.catalog.mapper.BookMapper;
import com.vasav.springmodulithlibrarymanagement.catalog.repository.AuthorRepository;
import com.vasav.springmodulithlibrarymanagement.catalog.repository.BookRepository;
import com.vasav.springmodulithlibrarymanagement.catalog.repository.CategoryRepository;
import com.vasav.springmodulithlibrarymanagement.catalog.repository.PublisherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;
    private final PublisherRepository publisherRepository;
    private final BookMapper bookMapper;

    @Transactional
    public BookResponse createBook(BookCreateRequest request) {
        validateIsbn(request.isbn());
        Book book = bookMapper.toEntity(request);
        book.setCategory(
                categoryRepository.findById(request.categoryId())
                        .orElseThrow(() -> new CategoryNotFoundException(
                                        "Category not found for id: " + request.categoryId()
                                )
                        )
        );

        if (request.publisherId() != null) {
            book.setPublisher(
                    publisherRepository.findById(request.publisherId())
                            .orElseThrow(() -> new PublisherNotFoundException(
                                            "Publisher not found for id: "
                                                    + request.publisherId()
                                    )
                            )
            );
        }

        book = bookRepository.save(book);

        book.setAuthors(
                createBookAuthors(book, request.authorIds())
        );

        return bookMapper.toResponse(
                bookRepository.save(book)
        );
    }

    @Transactional
    public BookResponse updateBook(Long id, BookUpdateRequest request) {
        Book book = getBook(id);
        if (request.isbn() != null && !request.isbn().equals(book.getIsbn()) && bookRepository.existsByIsbn(request.isbn())) {
            throw new DuplicateIsbnException(
                    "Book already exists with ISBN: " + request.isbn()
            );
        }

        bookMapper.updateEntityFromRequest(request, book);

        if (request.categoryId() != null) {
            book.setCategory(
                    categoryRepository.findById(request.categoryId())
                            .orElseThrow(() -> new CategoryNotFoundException(
                                            "Category not found for id: " + request.categoryId()
                                    )
                            )
            );
        }

        if (request.publisherId() != null) {
            book.setPublisher(
                    publisherRepository.findById(request.publisherId())
                            .orElseThrow(() ->
                                    new PublisherNotFoundException("Publisher not found for id: " + request.publisherId())
                            )
            );
        }

        if (request.authorIds() != null) {
            book.getAuthors().clear();
            book.getAuthors().addAll(
                    createBookAuthors(book, request.authorIds())
            );
        }

        return bookMapper.toResponse(
                bookRepository.save(book)
        );
    }

    @Transactional(readOnly = true)
    public BookResponse getBookById(Long id) {
        return bookMapper.toResponse(getBook(id));
    }

    @Transactional(readOnly = true)
    public List<BookResponse> getActiveBooks() {
        return bookRepository.findAllByActiveTrueOrderByTitleAsc()
                .stream()
                .map(bookMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<BookResponse> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(bookMapper::toResponse)
                .toList();
    }

    @Transactional
    public BookResponse setActive(Long id, boolean active) {
        Book book = getBook(id);
        book.setActive(active);

        return bookMapper.toResponse(
                bookRepository.save(book)
        );
    }

    private Book getBook(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(
                                "Book not found for id: " + id
                        )
                );
    }

    private void validateIsbn(String isbn) {
        if (isbn != null && bookRepository.existsByIsbn(isbn)) {
            throw new DuplicateIsbnException(
                    "Book already exists with ISBN: " + isbn
            );
        }
    }

    private List<BookAuthor> createBookAuthors(Book book, List<Long> authorIds) {
        List<BookAuthor> bookAuthors = new ArrayList<>();

        for (short i = 0; i < authorIds.size(); i++) {
            Long authorId = authorIds.get(i);
            var author = authorRepository.findById(authorId)
                    .orElseThrow(() -> new AuthorNotFoundException(
                                    "Author not found for id: " + authorId
                            )
                    );

            BookAuthor bookAuthor = BookAuthor.builder()
                    .id(new BookAuthorId(book.getId(), authorId))
                    .book(book)
                    .author(author)
                    .authorOrder(i)
                    .build();

            bookAuthors.add(bookAuthor);
        }

        return bookAuthors;
    }
}