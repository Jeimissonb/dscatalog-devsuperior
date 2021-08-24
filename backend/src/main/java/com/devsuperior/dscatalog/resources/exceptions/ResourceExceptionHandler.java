package com.devsuperior.dscatalog.resources.exceptions;

import java.io.Serializable;
import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.devsuperior.dscatalog.services.exceptions.EntityNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@ExceptionHandler
	public ResponseEntity<StandardError> entityNotFound (EntityNotFoundException e, HttpServletRequest http){
		StandardError err = new StandardError();
		err.setTimeStamp(Instant.now());
		err.setStatus(HttpStatus.NOT_FOUND.value());
		err.setError("Resource not found");
		err.setMessage(e.getMessage());
		err.setPath(http.getRequestURI());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}

}
