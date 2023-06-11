package br.com.compassuol.sp.challenge.ecommerce.exceptions;

public class EmptyProductException extends RuntimeException {
    public EmptyProductException(String message) {
        super(message);
    }
}
