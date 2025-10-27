package com.university.advice;

import com.university.exceptions.StudentNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(StudentNotFoundException.class)
    public ProblemDetail handleException(StudentNotFoundException studentNotFoundException) {
        var problemeDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, studentNotFoundException.getMessage());
        problemeDetail.setTitle("Student not found");
        return problemeDetail;
    }
}
