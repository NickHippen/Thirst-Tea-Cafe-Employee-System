package com.thirstteacafe.employees.exceptions;

/**
 * The error response that will be sent on a caught exception in a controller
 */
public class ErrorResponse {

	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
