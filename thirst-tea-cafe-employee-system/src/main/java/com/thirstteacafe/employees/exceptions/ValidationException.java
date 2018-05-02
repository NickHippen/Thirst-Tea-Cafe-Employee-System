package com.thirstteacafe.employees.exceptions;

/**
 * Describes an exception that occurs due to invalid input data
 */
public class ValidationException extends Exception {

	public ValidationException(String message) {
		super(message);
	}
	
}
