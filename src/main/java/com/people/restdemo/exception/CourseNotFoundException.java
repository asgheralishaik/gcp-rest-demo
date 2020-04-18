package com.people.restdemo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown if Course if not found
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class CourseNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public CourseNotFoundException(String message) {
        super(message);
    }

    public CourseNotFoundException(String message, Throwable t) {
        super(message, t);
    }
}
