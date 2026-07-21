package com.vasav.springmodulithlibrarymanagement.address.internal;

import com.vasav.springmodulithlibrarymanagement.address.api.AddressNotFoundException;
import com.vasav.springmodulithlibrarymanagement.address.api.AddressRequest;
import com.vasav.springmodulithlibrarymanagement.address.api.AddressResponse;
import com.vasav.springmodulithlibrarymanagement.address.api.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository repository;

    @Override
    public AddressResponse create(AddressRequest request) {
        Address address = new Address();
        mapRequest(request, address);

        Address saved = repository.save(address);
        return toResponse(saved);
    }

    @Override
    public AddressResponse getById(Long id) {
        Address address = repository.findById(id)
                .orElseThrow(() -> new AddressNotFoundException(id));

        return toResponse(address);
    }

    @Override
    public AddressResponse update(Long id, AddressRequest request) {
        Address address = repository.findById(id)
                .orElseThrow(() -> new AddressNotFoundException(id));

        mapRequest(request, address);

        Address updated = repository.save(address);
        return toResponse(updated);
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new AddressNotFoundException(id);
        }
        repository.deleteById(id);
    }

    private void mapRequest(AddressRequest request, Address address) {
        address.setStreet(request.street());
        address.setCity(request.city());
        address.setStateProvince(request.stateProvince());
        address.setPostalCode(request.postalCode());
        address.setCountry(request.country());
    }

    private AddressResponse toResponse(Address address) {
        return new AddressResponse(
                address.getId(),
                address.getStreet(),
                address.getCity(),
                address.getStateProvince(),
                address.getPostalCode(),
                address.getCountry(),
                address.getCreatedAt(),
                address.getUpdatedAt()
        );
    }
}