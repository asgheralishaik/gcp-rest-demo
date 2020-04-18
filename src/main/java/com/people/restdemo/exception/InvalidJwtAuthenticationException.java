package com.people.restdemo.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * Handles Token validation Exceptions
 */
public class InvalidJwtAuthenticationException extends AuthenticationException {

	private static final long serialVersionUID = -761503632186596342L;

	public InvalidJwtAuthenticationException(String e) {
        super(e);
    }
}