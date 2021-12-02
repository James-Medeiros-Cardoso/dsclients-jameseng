package com.jameseng.dsclients.resources.exceptions;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.jameseng.dsclients.services.exceptions.DataBaseException;
import com.jameseng.dsclients.services.exceptions.ResourceNotFoundException;

@ControllerAdvice //vai interceptar e tratar todas as exceções da camada resource (controlador REST)
public class ResourceExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class) //para saber o tipo de exceção que ele vai interceptar
	public ResponseEntity<StandardError> entityNotFound(ResourceNotFoundException e, HttpServletRequest request){
		HttpStatus status=HttpStatus.NOT_FOUND;
		
		StandardError err=new StandardError();
		err.setTimestemp(Instant.now());
		err.setStatus(status.value()); //.value() = chama o numero inteiro do NOT_FOUND
		err.setError("Resource not found");
		err.setMessage(e.getMessage());
		err.setPath(request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
	
	//EXCEÇÃO DA DELEÇÃO DE UM CLIENT QUE COMPROMETE A INTEGRIDADE DO SISTEMA
	@ExceptionHandler(DataBaseException.class) //para saber o tipo de exceção que ele vai interceptar
	public ResponseEntity<StandardError> database(DataBaseException e, HttpServletRequest request){
		HttpStatus status=HttpStatus.BAD_REQUEST;
		
		StandardError err=new StandardError();
		err.setTimestemp(Instant.now());
		err.setStatus(status.value()); //BAD_REQUEST = erro 400
		err.setError("Database exception");
		err.setMessage(e.getMessage());
		err.setPath(request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
}
