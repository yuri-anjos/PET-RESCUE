package br.com.petrescue.api.exceptions;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public abstract class ErroAbstratoException extends RuntimeException {

    private final String mensagem;
    private final HttpStatus status;

    public ErroAbstratoException(String mensagem, HttpStatus status) {
        super(mensagem);
        this.mensagem = mensagem;
        this.status = status;
    }
}
