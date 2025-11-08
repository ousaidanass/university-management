package com.university.config;

import com.university.kafka.consumer.AddressCreatedConsumer;
import com.university.kafka.consumer.AddressCreationFailedConsumer;
import com.university.kafka.events.AddressCreatedEvent;
import com.university.kafka.events.AddressCreationFailedEvent;
import com.university.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
@RequiredArgsConstructor
public class KafkaConsumerConfig {

    private final AddressCreationFailedConsumer addressCreationFailedConsumer;

    private final AddressCreatedConsumer addressCreatedConsumer;


    @Bean
    public Consumer<AddressCreationFailedEvent> addressCreationFailedEventListener(StudentService studentService) {
        return addressCreationFailedConsumer::newEvent;
    }


    @Bean
    public Consumer<AddressCreatedEvent> addressCreatedEventListener(StudentService studentService) {
        return addressCreatedConsumer::newEvent;
    }


}
