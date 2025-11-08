package com.university.kafka.producer;

import com.university.kafka.KafkaTopic;
import com.university.kafka.events.AddressCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AddressCreatedProducer {
    private final StreamBridge streamBridge;

    public void sendMessage(KafkaTopic kafkaTopic, AddressCreatedEvent message) {
        streamBridge.send(kafkaTopic.getTopicName(), message);
    }
}
