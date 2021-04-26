package br.com.petrescue.api.exceptions;

import org.springframework.http.HttpStatus;

public class NaoEncontradoException extends ErroAbstratoException {

    public NaoEncontradoException(String error) {
        super(error, HttpStatus.NOT_FOUND);
    }
}
