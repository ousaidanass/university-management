package com.university.kafka.consumer;

import com.university.kafka.events.AddressCreatedEvent;
import com.university.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AddressCreatedConsumer {
    private final StudentService studentService;

    public void newEvent(AddressCreatedEvent event) {
        studentService.handleAddressCreated(event.addressId(), event.studentId());
    }
}
