package com.university.kafka.producer;

import com.university.kafka.KafkaTopic;
import com.university.kafka.events.AddressCreationFailedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AddressCreationFailedProducer {
    private final StreamBridge streamBridge;

    public void sendMessage(KafkaTopic kafkaTopic, AddressCreationFailedEvent message) {
        streamBridge.send(kafkaTopic.getTopicName(), message);
    }
}
