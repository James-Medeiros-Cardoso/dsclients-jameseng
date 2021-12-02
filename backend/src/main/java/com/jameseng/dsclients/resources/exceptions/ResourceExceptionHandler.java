package com.jameseng.dsclients.resources.exceptions;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.jameseng.dsclients.services.exceptions.ResourceNotFoundException;

@ControllerAdvice //vai interceptar e tratar todas as exceções da camada resource (controlador REST)
public class ResourceExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class) //para saber o tipo de exceção que ele vai interceptar
	public ResponseEntity<StandardError> entityNotFound(ResourceNotFoundException e, HttpServletRequest request){
		StandardError err=new StandardError();
		err.setTimestemp(Instant.now());
		err.setStatus(HttpStatus.NOT_FOUND.value()); //.value() = chama o numero inteiro do NOT_FOUND
		err.setError("Resource not found");
		err.setMessage(e.getMessage());
		err.setPath(request.getRequestURI());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}
}
