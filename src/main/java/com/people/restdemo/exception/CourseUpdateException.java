package com.people.restdemo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown if Data cannot be processed,because of input errors.
 */

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
public class CourseUpdateException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public CourseUpdateException(String message) {
        super(message);
    }

    public CourseUpdateException(String message, Throwable t) {
        super(message, t);
    }
}
