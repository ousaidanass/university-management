package com.university.service;

import com.university.dto.AddressDto;
import com.university.dto.CreateStudentRequest;
import com.university.dto.StudentCretionInitiatedDto;
import com.university.dto.StudentDto;
import com.university.entity.Student;
import com.university.exceptions.StudentNotFoundException;
import com.university.kafka.events.StudentCreatedEvent;
import com.university.kafka.KafkaTopic;
import com.university.kafka.producer.StudentCreatedProducer;
import com.university.repository.StudentRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class StudentService {

	private static final Logger logger = LoggerFactory.getLogger(StudentService.class.getName());

	private final StudentRepository studentRepository;

	private final WebClient addressWebClient;

	private final StudentCreatedProducer studentCreatedProducer;

	public StudentCretionInitiatedDto createStudent(CreateStudentRequest createStudentRequest) {
		logger.info("Creation of student : " + createStudentRequest);
		var student = new Student();
		student.setFirstName(createStudentRequest.firstName());
		student.setLastName(createStudentRequest.lastName());
		student.setEmail(createStudentRequest.email());
		studentRepository.save(student);
		logger.info("Student created now we produce of student created event");
		studentCreatedProducer.sendMessage(KafkaTopic.STUDENT_CREATED,
				new StudentCreatedEvent(student.getId(), createStudentRequest.street(), createStudentRequest.city()));
		return new StudentCretionInitiatedDto(student.getId(), "INITIATED");
	}

	@Transactional
	public void handleAddressCreated(long addressId, long studentId) {
		logger.info("Appending of created address to the student");
		var student = studentRepository.findById(studentId).orElseThrow(() ->
				new IllegalStateException("Student with id='" + studentId + "' not found"));
		student.setAddressId(addressId);
		studentRepository.save(student);
	}

	public void handleAddressCreationFailed(long studentId) {
		logger.info("Creation of address failed so now we will delete the student with id=" + studentId);
		studentRepository.deleteById(studentId);
		logger.info("Student with id='" + studentId + "' deleted");
	}


	public StudentDto getById(long id) {
		logger.info("Inside Student getById");
		var student = studentRepository.findById(id).orElseThrow(() -> new StudentNotFoundException(id));
		return new StudentDto(student, getAddressById(student.getAddressId()));
	}


	@CircuitBreaker(name="addressService", fallbackMethod = "fallbackGetAddressById")
	public AddressDto getAddressById(long addressId) {
		var addressResponse = addressWebClient.get()
				.uri("/api/address/getById/" + addressId)
				.retrieve().bodyToMono(AddressDto.class);
		return addressResponse.block();
	}

	public AddressDto fallbackGetAddressById(long addressId, Throwable th) {
		return new AddressDto(0, "", "");
	}
}
