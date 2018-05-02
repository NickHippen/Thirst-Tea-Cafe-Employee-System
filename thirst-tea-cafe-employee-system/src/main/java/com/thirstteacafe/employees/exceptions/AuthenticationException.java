package com.thirstteacafe.employees.exceptions;

/**
 * Describes an exception that occurs do to invalid authentication
 */
public class AuthenticationException extends Exception {

	public AuthenticationException(String message) {
		super(message);
	}
	
}
