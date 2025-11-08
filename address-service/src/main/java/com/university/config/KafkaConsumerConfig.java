package com.university.config;

import com.university.kafka.consumer.StudentCreatedConsumer;
import com.university.kafka.events.StudentCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
@RequiredArgsConstructor
public class KafkaConsumerConfig {

    private final StudentCreatedConsumer studentCreatedConsumer;

    @Bean
    public Consumer<StudentCreatedEvent> studentCreatedEventListner() {
        return studentCreatedConsumer::newEvent;
    }
}
