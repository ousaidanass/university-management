package com.university.service;

import com.university.dto.AddressDto;
import com.university.dto.CreateAddressRequest;
import com.university.entity.Address;
import com.university.exceptions.AddressNotFoundException;
import com.university.kafka.KafkaTopic;
import com.university.kafka.events.AddressCreatedEvent;
import com.university.kafka.events.AddressCreationFailedEvent;
import com.university.kafka.events.StudentCreatedEvent;
import com.university.kafka.producer.AddressCreatedProducer;
import com.university.kafka.producer.AddressCreationFailedProducer;
import com.university.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;

    private final AddressCreatedProducer addressCreatedProducer;
    private final AddressCreationFailedProducer addressCreationFailedProducer;

    private final Logger logger = LoggerFactory.getLogger(AddressService.class.getName());

    private Address saveAddress(String street, String city) {
        var address = new Address();
        address.setStreet(street);
        address.setCity(city);
        addressRepository.save(address);
        return address;
    }

    public AddressDto createAddress(CreateAddressRequest createAddressRequest) {
        logger.info("Creating address: " + createAddressRequest);
        var address = saveAddress(createAddressRequest.street(), createAddressRequest.city());
        return new AddressDto(address);
    }

    public void createAddressFromEvent(StudentCreatedEvent event) {
        logger.info("Creating address from event: " + event);
        Address address;
        try {
            address = saveAddress(event.street(), event.city());
        } catch (Exception e) {
            logger.error("Error while creating address in database, now producing event AddressCreationFailed");
            addressCreationFailedProducer.sendMessage(KafkaTopic.ADDRESS_CREATION_FAILED,
                    new AddressCreationFailedEvent(event.studentId()));
            return;
        }
        logger.info("Address created from event, now producing event AddressCreated");
        addressCreatedProducer.sendMessage(KafkaTopic.ADDRESS_CREATED, new AddressCreatedEvent(address.getId(), event.studentId()));
    }

    public AddressDto getById(long id) {
        logger.info("Getting address by id=" + id);
        var address = addressRepository.findById(id).orElseThrow(() -> new AddressNotFoundException(id));
        return new AddressDto(address);
    }
}
