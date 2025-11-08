package com.university.kafka.consumer;

import com.university.kafka.events.AddressCreationFailedEvent;
import com.university.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AddressCreationFailedConsumer {
    private final StudentService studentService;

    public void newEvent(AddressCreationFailedEvent event) {
        studentService.handleAddressCreationFailed(event.studentId());
    }
}
