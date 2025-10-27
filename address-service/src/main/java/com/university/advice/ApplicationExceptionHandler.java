package com.university.advice;

import com.university.exceptions.AddressNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(AddressNotFoundException.class)
    public ProblemDetail handleException(AddressNotFoundException addressNotFoundException) {
        var problem = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, addressNotFoundException.getMessage());
        problem.setTitle("Customer Not Found");
        return problem;
    }
}
