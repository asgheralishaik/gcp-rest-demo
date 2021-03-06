package com.people.restdemo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Handles Invalid data Exception
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidDataException extends Exception {

    private static final long serialVersionUID = 1L;

    public InvalidDataException(String message) {
        super(message);
    }

    public InvalidDataException(String message, Throwable t) {
        super(message, t);
    }
}
