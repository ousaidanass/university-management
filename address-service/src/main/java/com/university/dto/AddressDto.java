package com.university.dto;

import com.university.entity.Address;

public record AddressDto(long addressId, String street, String city) {
    public AddressDto(Address address) {
        this(address.getId(), address.getStreet(), address.getCity());
    }
}
