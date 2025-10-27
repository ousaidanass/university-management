package com.university.dto;


import com.university.entity.Student;

public record StudentDto(long id, String firstName, String lastName, String email, AddressDto addressDto) {
    public StudentDto(Student student, AddressDto addressDto) {
        this(student.getId(), student.getFirstName(), student.getLastName(), student.getEmail(),
                addressDto);
    }

}
