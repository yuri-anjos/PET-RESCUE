package br.com.petrescue.api.exceptions;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public abstract class ErroAbstratoException extends RuntimeException {



    private final String message;
    private final HttpStatus status;

    public ErroAbstratoException(String message, HttpStatus status) {
        super(message);
        this.status = status;
        this.message = message;
    }
}
