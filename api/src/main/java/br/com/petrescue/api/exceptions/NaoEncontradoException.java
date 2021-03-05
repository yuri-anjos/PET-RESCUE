package br.com.petrescue.api.exceptions;

import org.springframework.http.HttpStatus;

public class NaoEncontradoException extends ErroAbstratoException {

    public NaoEncontradoException(String mensagem) {
        super(mensagem, HttpStatus.NOT_FOUND);
    }
}
