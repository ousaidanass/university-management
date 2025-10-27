package com.university.controller;

import com.university.dto.AddressDto;
import com.university.dto.CreateAddressRequest;
import com.university.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/address")
@RequiredArgsConstructor
public class AddressController {
    private final AddressService addressService;

    @PostMapping("/create")
    public AddressDto createAddress(@RequestBody CreateAddressRequest createAddressRequest) {
        return addressService.createAddress(createAddressRequest);
    }

    @GetMapping("/getById/{id}")
    public AddressDto getById(@PathVariable long id) {
        return addressService.getById(id);
    }
}
