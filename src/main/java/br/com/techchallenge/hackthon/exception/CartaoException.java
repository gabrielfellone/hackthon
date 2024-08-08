package br.com.techchallenge.hackthon.exception;

import java.io.Serial;

public class CartaoException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1L;
    public CartaoException(String message, Exception e) {super(message, e);}

    public CartaoException(String message) {super(message);}
}
