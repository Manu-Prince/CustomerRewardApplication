package com.rewardapp.rewardexception;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.bind.MissingServletRequestParameterException;
/*Global exception handler for REST API controllers.

	Handles specific exceptions like CustomerNotFoundException by returning
	404 Not Found response, and catches all other exceptions to return
	400 Bad Request response with a generic error message.*/

@RestControllerAdvice
public class APIExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleAll(Exception ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + ex.getMessage());

	}

	@ExceptionHandler(CustomerNotFoundException.class)
	public ResponseEntity<String> handleCustomerNF(CustomerNotFoundException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	}

	@ExceptionHandler(InvalidDateFormatException.class)
	public ResponseEntity<ErrorDetails> handleInvalidDateFormatException(
	        InvalidDateFormatException ex, WebRequest request) {
	    
	    ErrorDetails errorDetails = new ErrorDetails(
	        HttpStatus.BAD_REQUEST.value(),
	        ex.getMessage(),
	        request.getDescription(false)
	    );

	    return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(InvalidDateRangeException.class)
	public ResponseEntity<String> handleInvalidDate(InvalidDateRangeException ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());

	}

	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity<String> handleMissingParams(MissingServletRequestParameterException ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body("Missing request parameter: " + ex.getParameterName());
	}

	@ExceptionHandler(RewardCalculationException.class)
	public ResponseEntity<String> handleRewardError(RewardCalculationException ex) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body("Reward calculation error: " + ex.getMessage());

	}
}
