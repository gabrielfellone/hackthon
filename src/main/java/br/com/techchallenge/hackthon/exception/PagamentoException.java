package br.com.techchallenge.hackthon.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(HttpStatus.PAYMENT_REQUIRED)
public class PagamentoException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1L;
    public PagamentoException(String message, Exception e) {super(message, e);}

    public PagamentoException(String message) {super(message);}
}
