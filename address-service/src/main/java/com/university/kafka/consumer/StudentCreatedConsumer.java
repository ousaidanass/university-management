package com.university.kafka.consumer;

import com.university.kafka.events.StudentCreatedEvent;
import com.university.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StudentCreatedConsumer {

    private final AddressService addressService;

    public void newEvent(StudentCreatedEvent event) {
        addressService.createAddressFromEvent(event);
    }
}
