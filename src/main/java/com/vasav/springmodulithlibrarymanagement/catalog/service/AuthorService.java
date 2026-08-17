package com.vasav.springmodulithlibrarymanagement.catalog.service;

import com.vasav.springmodulithlibrarymanagement.catalog.dto.request.AuthorCreateRequest;
import com.vasav.springmodulithlibrarymanagement.catalog.dto.request.AuthorUpdateRequest;
import com.vasav.springmodulithlibrarymanagement.catalog.dto.response.AuthorResponse;
import com.vasav.springmodulithlibrarymanagement.catalog.exception.AuthorNotFoundException;
import com.vasav.springmodulithlibrarymanagement.catalog.mapper.AuthorMapper;
import com.vasav.springmodulithlibrarymanagement.catalog.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    @Transactional
    public AuthorResponse createAuthor(AuthorCreateRequest request) {
        return authorMapper.toResponse(
                authorRepository.save(authorMapper.toEntity(request))
        );
    }

    @Transactional
    public AuthorResponse updateAuthor(Long id, AuthorUpdateRequest request) {
        var author = getAuthor(id);

        authorMapper.updateEntityFromRequest(request, author);

        return authorMapper.toResponse(authorRepository.save(author));
    }

    @Transactional(readOnly = true)
    public AuthorResponse getAuthorById(Long id) {
        return authorMapper.toResponse(getAuthor(id));
    }

    @Transactional(readOnly = true)
    public List<AuthorResponse> getAllAuthors() {
        return authorRepository.findAll()
                .stream()
                .map(authorMapper::toResponse)
                .toList();
    }

    private com.vasav.springmodulithlibrarymanagement.catalog.entity.Author getAuthor(Long id) {
        return authorRepository.findById(id).orElseThrow(() ->
                new AuthorNotFoundException(
                        "Author not found for id: " + id
                )
        );
    }
}