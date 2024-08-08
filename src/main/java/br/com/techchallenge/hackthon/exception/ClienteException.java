package br.com.techchallenge.hackthon.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class ClienteException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1L;
    public ClienteException(String message, Exception e) {super(message, e);}

    public ClienteException(String message) {super(message);}
}
