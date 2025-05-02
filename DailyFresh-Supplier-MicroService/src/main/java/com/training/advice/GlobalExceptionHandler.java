package com.training.advice;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.training.dto.response.ErrorResponse;
import com.training.exception.SupplierNotFoundException;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(exception = SupplierNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleSupplierNotFoundException(Exception e) {
		System.out.println(e.getMessage());
		ErrorResponse response = new ErrorResponse();
		response.setStatusCode(HttpStatus.NOT_FOUND.value());
		response.setErrorMessage(e.getMessage());
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(exception = ConstraintViolationException.class)
	public ResponseEntity<ErrorResponse> handleValidationErrors(ConstraintViolationException e) {
		List<String> fieldErrors = e.getConstraintViolations().stream().map(ConstraintViolation::getMessage)
				.collect(Collectors.toList());

		ErrorResponse response = new ErrorResponse();
		response.setStatusCode(HttpStatus.BAD_REQUEST.value());
		response.setErrorMessage(fieldErrors.toString());
		
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(exception = Exception.class)
	public ResponseEntity<ErrorResponse> handleGenericException(Exception e) {
		System.out.println(e.getMessage());
		ErrorResponse response = new ErrorResponse();
		response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		response.setErrorMessage(e.getMessage());
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}