package br.com.compassuol.sp.challenge.ecommerce.exceptions;

public class InvalidCpfOrEmailException extends RuntimeException {

    public InvalidCpfOrEmailException(String message) {
        super(message);
    }

}
