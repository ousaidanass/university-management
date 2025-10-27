package com.university.controller;


import com.university.dto.CreateStudentRequest;
import com.university.dto.StudentDto;
import com.university.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/student")
@RequiredArgsConstructor
public class StudentController {
	
	private final StudentService studentService;
	
	@PostMapping("/create")
	public StudentDto createStudent (@RequestBody CreateStudentRequest createStudentRequest) {
		return studentService.createStudent(createStudentRequest);
	}
	
	@GetMapping("/getById/{id}")
	public StudentDto getById (@PathVariable long id) {
		return studentService.getById(id);
	}
	
}
