package br.com.petrescue.api.interceptor;

import br.com.petrescue.api.exceptions.ErroAbstratoException;
import br.com.petrescue.api.exceptions.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestControllerAdvice
public class ControllerErrorInterceptor {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(Exception exception) {

        ErrorResponse response = new ErrorResponse();
        response.setMessage("Ocorreu um erro inesperado!");
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());

        log.error("Ocorreu um erro", exception);

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(ResponseStatusException exception) {

        ErrorResponse response = new ErrorResponse();
        response.setMessage(exception.getReason());
        response.setStatus(exception.getStatus().value());

        log.error("Ocorreu um erro", exception);

        return new ResponseEntity<>(response, exception.getStatus());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(MethodArgumentNotValidException exception) {

        FieldError fieldError = exception.getBindingResult().getFieldError();

        ErrorResponse response = new ErrorResponse();
        response.setMessage(fieldError.getField() + " " + fieldError.getDefaultMessage());
        response.setStatus(HttpStatus.BAD_REQUEST.value());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(ErroAbstratoException exception) {

        ErrorResponse response = new ErrorResponse();
        response.setMessage(exception.getMessage());
        response.setStatus(exception.getStatus().value());

        return new ResponseEntity<>(response, exception.getStatus());
    }
}