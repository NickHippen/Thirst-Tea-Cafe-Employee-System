package edu.unomaha.whatever.simplyscoresrest.exceptions;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandlingController {

	@ResponseStatus(value=HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(AuthenticationException.class)
	@ResponseBody
	public ErrorResponse handleAuthenticationError(HttpServletRequest req, AuthenticationException e) {
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setMessage(e.getMessage());
		return errorResponse;
	}
	
	@ResponseStatus(value=HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ValidationException.class)
	@ResponseBody
	public ErrorResponse handleValidationError(HttpServletRequest req, ValidationException e) {
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setMessage(e.getMessage());
		return errorResponse;
	}
	
}
