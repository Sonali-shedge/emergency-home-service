package com.EventManagementSystem.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.EventManagementSystem.config.ApiError;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ApiError> handleUserNotFound(UserNotFoundException ex) {

		ApiError error = new ApiError(HttpStatus.NOT_FOUND.value(), ex.getMessage());

		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(AddressNotFoundException.class)
	public ResponseEntity<ApiError> handleAddressNotFound(AddressNotFoundException ex) {
		ApiError error = new ApiError(HttpStatus.NOT_FOUND.value(), ex.getMessage());

		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(ServiceCategoryNotFoundException.class)
	public ResponseEntity<ApiError> handleServiceCategoryNotFound(ServiceCategoryNotFoundException ex) {
		ApiError error = new ApiError(HttpStatus.NOT_FOUND.value(), ex.getMessage());

		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(InvalidCredentialsException.class)
	public ResponseEntity<ApiError> handleInvalidCredentials(InvalidCredentialsException ex) {
		ApiError error = new ApiError(HttpStatus.NOT_FOUND.value(), ex.getMessage());

		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(RoleNotFoundException.class)
	public ResponseEntity<ApiError> handleRoleNotFound(RoleNotFoundException ex) {
		ApiError error = new ApiError(HttpStatus.NOT_FOUND.value(), ex.getMessage());

		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(ServiceRequestNotFoundException.class)
	public ResponseEntity<ApiError> handleServiceRequestNotFound(ServiceRequestNotFoundException ex) {
		ApiError error = new ApiError(HttpStatus.NOT_FOUND.value(), ex.getMessage());

		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(ServiceProviderNotFoundException.class)
	public ResponseEntity<ApiError> handleServiceProviderNotFound(ServiceProviderNotFoundException ex) {
		ApiError error = new ApiError(HttpStatus.NOT_FOUND.value(), ex.getMessage());

		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiError> handleValidationErrors(MethodArgumentNotValidException ex) {

		ApiError error = new ApiError(HttpStatus.NOT_FOUND.value(), ex.getMessage());

		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}

}
