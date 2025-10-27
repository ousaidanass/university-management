package com.university.dto;

public record CreateStudentRequest(String firstName, String lastName, String email, long addressId) {

}
