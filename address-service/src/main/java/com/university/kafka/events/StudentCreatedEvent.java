package com.university.kafka.events;

public record StudentCreatedEvent(long studentId, String street, String city) {
}
