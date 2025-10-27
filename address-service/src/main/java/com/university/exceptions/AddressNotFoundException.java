package com.university.exceptions;

public class AddressNotFoundException extends RuntimeException {
    private static final String MESSAGE = "Address with id=%d not found";

    public AddressNotFoundException(long id) {
        super(MESSAGE.formatted(id));
    }
}
