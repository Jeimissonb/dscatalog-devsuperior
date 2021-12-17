package com.devsuperior.dscatalog.resources.exceptions;

import java.io.Serializable;
import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.devsuperior.dscatalog.services.exceptions.DataBaseException;
import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@ExceptionHandler
	public ResponseEntity<StandardError> entityNotFound (ResourceNotFoundException e, HttpServletRequest http){
		HttpStatus status = HttpStatus.NOT_FOUND;

		StandardError err = new StandardError();
		err.setTimeStamp(Instant.now());
		err.setStatus(status.value());
		err.setError("Resource not found");
		err.setMessage(e.getMessage());
		err.setPath(http.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
	

	@ExceptionHandler
	public ResponseEntity<StandardError> dataBaseException (DataBaseException e, HttpServletRequest http){
		HttpStatus status = HttpStatus.BAD_REQUEST;
		StandardError err = new StandardError();
		err.setTimeStamp(Instant.now());
		err.setStatus(status.value());
		err.setError("Database Exception");
		err.setMessage(e.getMessage());
		err.setPath(http.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler
	public ResponseEntity<ValidationError> validation (MethodArgumentNotValidException e, HttpServletRequest http){
		HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
		ValidationError err = new ValidationError();
		err.setTimeStamp(Instant.now());
		err.setStatus(status.value());
		err.setError("Validation Exception");
		err.setMessage(e.getMessage());
		err.setPath(http.getRequestURI());
		
		
		for (FieldError f : e.getBindingResult().getFieldErrors()) {
			err.addError(f.getField(), f.getDefaultMessage());
		}
		
		return ResponseEntity.status(status).body(err);
	}

}
