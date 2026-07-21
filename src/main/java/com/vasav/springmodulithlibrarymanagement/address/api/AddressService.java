package com.vasav.springmodulithlibrarymanagement.address.api;

public interface AddressService {

    AddressResponse create(AddressRequest request);

    AddressResponse getById(Long id);

    AddressResponse update(Long id, AddressRequest request);

    void delete(Long id);
}