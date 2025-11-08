package com.university.kafka.events;

public record AddressCreatedEvent(long addressId, long studentId) {
}
