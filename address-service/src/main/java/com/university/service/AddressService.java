package com.university.service;

import com.university.dto.AddressDto;
import com.university.dto.CreateAddressRequest;
import com.university.entity.Address;
import com.university.exceptions.AddressNotFoundException;
import com.university.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;
    private final Logger logger = LoggerFactory.getLogger(AddressService.class.getName());

    public AddressDto createAddress(CreateAddressRequest createAddressRequest) {
        var address = new Address();
        address.setStreet(createAddressRequest.street());
        address.setCity(createAddressRequest.city());
        addressRepository.save(address);
        return new AddressDto(address);
    }

    public AddressDto getById(long id) {
        logger.info("Getting address by id=" + id);
        var address = addressRepository.findById(id).orElseThrow(() -> new AddressNotFoundException(id));
        return new AddressDto(address);
    }
}
