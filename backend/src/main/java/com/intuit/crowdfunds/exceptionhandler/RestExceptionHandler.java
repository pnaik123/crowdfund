package com.intuit.crowdfunds.exceptionhandler;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.intuit.crowdfunds.dtos.ErrorDTO;
import com.intuit.crowdfunds.exceptions.BadRequestException;
import com.intuit.crowdfunds.exceptions.ProjectCreationException;
import com.intuit.crowdfunds.exceptions.ResourceNotFoundException;
import com.intuit.crowdfunds.exceptions.UnauthorizedException;
import com.intuit.crowdfunds.utility.LoggingUtil;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class RestExceptionHandler {
	private final Logger logger = LoggingUtil.getLogger(getClass());

	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseBody
	public ResponseEntity<ErrorDTO> handleResourceNotFoundException(ResourceNotFoundException ex) {
		logger.error("Project creation exception: {}", ex.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDTO(ex.getMessage(), HttpStatus.NOT_FOUND));
	}

	@ExceptionHandler(BadRequestException.class)
	@ResponseBody
	public ResponseEntity<ErrorDTO> handleBadRequestException(BadRequestException ex) {
		logger.error("Project creation exception: {}", ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new ErrorDTO(ex.getMessage(), HttpStatus.BAD_REQUEST));
	}

	@ExceptionHandler(UnauthorizedException.class)
	@ResponseBody
	public ResponseEntity<ErrorDTO> handleUnauthorizedException(UnauthorizedException ex) {
		logger.error("Project creation exception: {}", ex.getMessage());
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
				.body(new ErrorDTO(ex.getMessage(), HttpStatus.UNAUTHORIZED));
	}

	@ExceptionHandler(ProjectCreationException.class)
	public ResponseEntity<ErrorDTO> handleProjectCreationException(ProjectCreationException ex) {
		logger.error("Project creation exception: {}", ex.getMessage());
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
				.body(new ErrorDTO(ex.getMessage(), HttpStatus.UNAUTHORIZED));
	}

	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseBody
	public ResponseEntity<Map<String, Object>> handleMethodArgumentNotValidException(ConstraintViolationException ex) {
		logger.error("Project creation exception: {}", ex.getMessage());
        Map<String, Object> errors = new HashMap<>();

        Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
        for (ConstraintViolation<?> violation : violations) {
            String fieldName = violation.getPropertyPath().toString();
            String errorMessage = violation.getMessage();
            errors.put(fieldName, errorMessage);
        }
		errors.put("status", HttpStatus.BAD_REQUEST);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseBody
	public ResponseEntity<Map<String, Object>> handleConstraintViolationException(MethodArgumentNotValidException ex) {
		logger.error("Project creation exception: {}", ex.getMessage());
		Map<String, Object> errors = new HashMap<>();

		ex.getBindingResult().getAllErrors().forEach(error -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		
		errors.put("status", HttpStatus.BAD_REQUEST);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
	}

	@ExceptionHandler(Exception.class)
	@ResponseBody
	public ResponseEntity<ErrorDTO> handleGlobalException(Exception ex) {
		logger.error("Generic exception: {}", ex.getMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new ErrorDTO(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR));
	}
}