package com.jameseng.dsclients.resources.exceptions;

import com.jameseng.dsclients.services.exceptions.DataBaseException;
import com.jameseng.dsclients.services.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

@ControllerAdvice //vai interceptar e tratar todas as exceções da camada resource (controlador REST)
public class ResourceExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class) //para saber o tipo de exceção que ele vai interceptar
    public ResponseEntity<StandardError> entityNotFound(ResourceNotFoundException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;

        StandardError err = new StandardError();
        err.setTimestemp(Instant.now());
        err.setStatus(status.value()); //.value() = chama o numero inteiro do NOT_FOUND
        err.setError("Resource not found");
        err.setMessage(e.getMessage());
        err.setPath(request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    //EXCEÇÃO DA DELEÇÃO DE UM CLIENT QUE COMPROMETE A INTEGRIDADE DO SISTEMA
    @ExceptionHandler(DataBaseException.class) //para saber o tipo de exceção que ele vai interceptar
    public ResponseEntity<StandardError> database(DataBaseException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        StandardError err = new StandardError();
        err.setTimestemp(Instant.now());
        err.setStatus(status.value()); //BAD_REQUEST = erro 400
        err.setError("Database exception");
        err.setMessage(e.getMessage());
        err.setPath(request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationError> validation(MethodArgumentNotValidException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY; // 422 = entidade não pode ser processada

        ValidationError error = new ValidationError();
        error.setTimestemp(Instant.now());
        error.setStatus(status.value());
        error.setError("Validation exception.");
        error.setMessage(e.getMessage());
        error.setPath(request.getRequestURI());

        // e.getBindingResult().getFieldErrors() = erros da validação
        for (FieldError f : e.getBindingResult().getFieldErrors()) {
            error.addError(f.getField(), f.getDefaultMessage());
        }
        return ResponseEntity.status(status).body(error);
    }
}