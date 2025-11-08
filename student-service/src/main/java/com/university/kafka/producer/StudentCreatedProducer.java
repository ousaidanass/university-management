package com.university.kafka.producer;

import com.university.kafka.KafkaTopic;
import com.university.kafka.events.StudentCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StudentCreatedProducer {

    private final StreamBridge streamBridge;

    public void sendMessage(KafkaTopic kafkaTopic, StudentCreatedEvent message) {
        streamBridge.send(kafkaTopic.getTopicName(), message);
    }
}
