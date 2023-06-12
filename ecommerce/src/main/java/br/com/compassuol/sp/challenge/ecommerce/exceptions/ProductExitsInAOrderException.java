package br.com.compassuol.sp.challenge.ecommerce.exceptions;

public class ProductExitsInAOrderException extends RuntimeException {
    public ProductExitsInAOrderException(String message) {
        super(message);
    }
}
