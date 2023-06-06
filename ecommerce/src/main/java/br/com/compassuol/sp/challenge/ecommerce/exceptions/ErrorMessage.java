package br.com.compassuol.sp.challenge.ecommerce.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ErrorMessage {
    private int status;
    private String message;
    private long timestamp;
}
