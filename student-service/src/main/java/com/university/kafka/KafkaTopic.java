package com.university.kafka;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum KafkaTopic {
    STUDENT_CREATED("studentCreated"),
    ADDRESS_CREATION_FAILED("addressCreationFailed"),
    ADDRESS_CREATED("addressCreated");

    private final String topicName;
}
