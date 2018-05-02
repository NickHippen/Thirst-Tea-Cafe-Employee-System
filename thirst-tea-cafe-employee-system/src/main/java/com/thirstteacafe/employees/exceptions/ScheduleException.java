package com.thirstteacafe.employees.exceptions;

/**
 * Describes an exception that occurs during scheduling
 */
public class ScheduleException extends Exception {

	public ScheduleException(String message, Throwable throwable) {
		super(message, throwable);
	}
	
	public ScheduleException(String message) {
		super(message);
	}
	
}