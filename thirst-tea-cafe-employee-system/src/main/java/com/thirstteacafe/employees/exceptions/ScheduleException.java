package com.thirstteacafe.employees.exceptions;

public class ScheduleException extends Exception {

	public ScheduleException(String message, Throwable throwable) {
		super(message, throwable);
	}
	
	public ScheduleException(String message) {
		super(message);
	}
	
}