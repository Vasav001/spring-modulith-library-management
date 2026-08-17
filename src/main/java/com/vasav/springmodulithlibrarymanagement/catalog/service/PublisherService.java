package com.vasav.springmodulithlibrarymanagement.catalog.service;

import com.vasav.springmodulithlibrarymanagement.catalog.dto.request.PublisherCreateRequest;
import com.vasav.springmodulithlibrarymanagement.catalog.dto.request.PublisherUpdateRequest;
import com.vasav.springmodulithlibrarymanagement.catalog.dto.response.PublisherResponse;
import com.vasav.springmodulithlibrarymanagement.catalog.entity.Publisher;
import com.vasav.springmodulithlibrarymanagement.catalog.exception.PublisherNotFoundException;
import com.vasav.springmodulithlibrarymanagement.catalog.mapper.AddressMapper;
import com.vasav.springmodulithlibrarymanagement.catalog.mapper.PublisherMapper;
import com.vasav.springmodulithlibrarymanagement.catalog.repository.PublisherRepository;
import com.vasav.springmodulithlibrarymanagement.shared.address.Address;
import com.vasav.springmodulithlibrarymanagement.shared.address.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PublisherService {

    private final PublisherRepository publisherRepository;
    private final PublisherMapper publisherMapper;
    private final AddressMapper addressMapper;
    private final AddressRepository addressRepository;

    @Transactional
    public PublisherResponse createPublisher(PublisherCreateRequest request) {
        Publisher publisher = publisherMapper.toEntity(request);

        if (request.address() != null) {
            Address address = addressRepository.save(
                    addressMapper.toEntity(request.address())
            );
            publisher.setAddress(address);
        }

        return publisherMapper.toResponse(
                publisherRepository.save(publisher)
        );
    }

    @Transactional
    public PublisherResponse updatePublisher(Long id, PublisherUpdateRequest request) {
        var publisher = getPublisher(id);
        publisherMapper.updateEntityFromRequest(request, publisher);
        if (request.address() != null) {
            if (publisher.getAddress() == null) {
                publisher.setAddress(
                        addressRepository.save(
                                addressMapper.toEntity(request.address())
                        )
                );
            } else {
                addressMapper.updateEntityFromRequest(
                        request.address(),
                        publisher.getAddress()
                );
            }
        }

        return publisherMapper.toResponse(
                publisherRepository.save(publisher)
        );
    }

    @Transactional(readOnly = true)
    public PublisherResponse getPublisherById(Long id) {
        return publisherMapper.toResponse(getPublisher(id));
    }

    @Transactional(readOnly = true)
    public List<PublisherResponse> getAllPublishers() {
        return publisherRepository.findAll()
                .stream()
                .map(publisherMapper::toResponse)
                .toList();
    }

    private com.vasav.springmodulithlibrarymanagement.catalog.entity.Publisher getPublisher(Long id) {
        return publisherRepository.findById(id).orElseThrow(() ->
                new PublisherNotFoundException(
                        "Publisher not found for id: " + id
                )
        );
    }
}