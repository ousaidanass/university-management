package com.university.service;

import com.university.dto.AddressDto;
import com.university.dto.CreateStudentRequest;
import com.university.dto.StudentDto;
import com.university.entity.Student;
import com.university.exceptions.StudentNotFoundException;
import com.university.repository.StudentRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class StudentService {

	private static final Logger logger = LoggerFactory.getLogger(StudentService.class.getName());

	private final StudentRepository studentRepository;

	private final WebClient addressWebClient;

	public StudentDto createStudent(CreateStudentRequest createStudentRequest) {
		var student = new Student();
		student.setFirstName(createStudentRequest.firstName());
		student.setLastName(createStudentRequest.lastName());
		student.setEmail(createStudentRequest.email());
		
		student.setAddressId(createStudentRequest.addressId());
		studentRepository.save(student);
		return new StudentDto(student, getAddressById(student.getAddressId()));
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
