package com.university.client;

import com.university.dto.AddressDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "api-gateway", path = "/address-service/api/address")
public interface AddressFeignCient {

    @GetMapping("/getById/{id}")
    AddressDto getById(@PathVariable long id);
}
