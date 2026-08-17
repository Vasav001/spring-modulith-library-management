package com.vasav.springmodulithlibrarymanagement.catalog.service;

import com.vasav.springmodulithlibrarymanagement.catalog.dto.request.BookCopyCreateRequest;
import com.vasav.springmodulithlibrarymanagement.catalog.dto.request.BookCopyUpdateRequest;
import com.vasav.springmodulithlibrarymanagement.catalog.dto.response.BookCopyResponse;
import com.vasav.springmodulithlibrarymanagement.catalog.entity.BookCopy;
import com.vasav.springmodulithlibrarymanagement.catalog.exception.BookCopyNotFoundException;
import com.vasav.springmodulithlibrarymanagement.catalog.exception.BookNotFoundException;
import com.vasav.springmodulithlibrarymanagement.catalog.mapper.BookCopyMapper;
import com.vasav.springmodulithlibrarymanagement.catalog.repository.BookCopyRepository;
import com.vasav.springmodulithlibrarymanagement.catalog.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookCopyService {

    private final BookCopyRepository bookCopyRepository;
    private final BookRepository bookRepository;
    private final BookCopyMapper bookCopyMapper;

    @Transactional
    public BookCopyResponse createBookCopy(BookCopyCreateRequest request) {
        if (!bookRepository.existsById(request.bookId())) {
            throw new BookNotFoundException(
                    "Book not found for id: " + request.bookId()
            );
        }

        if (request.barcode() != null && bookCopyRepository.existsByBarcode(request.barcode())) {
            throw new IllegalArgumentException(
                    "Book copy already exists with barcode: " + request.barcode()
            );
        }

        BookCopy bookCopy = bookCopyMapper.toEntity(request);

        bookCopy.setBook(
                bookRepository.getReferenceById(request.bookId())
        );

        return bookCopyMapper.toResponse(
                bookCopyRepository.save(bookCopy)
        );
    }

    @Transactional
    public BookCopyResponse updateBookCopy(Long id, BookCopyUpdateRequest request) {
        BookCopy bookCopy = getBookCopy(id);

        bookCopyMapper.updateEntityFromRequest(request, bookCopy);

        return bookCopyMapper.toResponse(
                bookCopyRepository.save(bookCopy)
        );
    }

    @Transactional(readOnly = true)
    public BookCopyResponse getBookCopyById(Long id) {
        return bookCopyMapper.toResponse(getBookCopy(id));
    }

    @Transactional(readOnly = true)
    public List<BookCopyResponse> getBookCopiesByBookId(Long bookId) {
        return bookCopyRepository.findAllByBookId(bookId)
                .stream()
                .map(bookCopyMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<BookCopyResponse> getBookCopiesByBranchId(Long branchId) {
        return bookCopyRepository.findAllByBranchId(branchId)
                .stream()
                .map(bookCopyMapper::toResponse)
                .toList();
    }

    private BookCopy getBookCopy(Long id) {
        return bookCopyRepository.findById(id)
                .orElseThrow(() -> new BookCopyNotFoundException(
                                "Book copy not found for id: " + id
                        )
                );
    }
}