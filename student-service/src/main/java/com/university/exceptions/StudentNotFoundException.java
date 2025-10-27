package com.university.exceptions;


public class StudentNotFoundException extends RuntimeException {
    private static final String MESSAGE = "Student with id=%d not found";

    public StudentNotFoundException(long id) {
        super(MESSAGE.formatted(id));
    }
}
