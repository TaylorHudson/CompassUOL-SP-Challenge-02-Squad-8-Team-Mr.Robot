package br.com.compassuol.sp.challenge.ecommerce.exceptions;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalErrorHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleGenericException(Exception ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErrorMessage error = createErrorResponse(status, ex);
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleResourceNotFoundException(ResourceNotFoundException ex) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ErrorMessage error = createErrorResponse(status, ex);
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(InvalidCpfOrEmailException.class)
    public ResponseEntity<ErrorMessage> handleInvalidCpfOrEmailException(InvalidCpfOrEmailException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErrorMessage error = createErrorResponse(status, ex);
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErrorMessage>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        List<ErrorMessage> errors = new ArrayList<>();

        BindingResult bindingResult = ex.getBindingResult();
        bindingResult.getFieldErrors()
                .forEach(error -> errors.add(createErrorResponse(status, error.getDefaultMessage())));

        return ResponseEntity.status(status).body(errors);
    }

    @ExceptionHandler(EmptyProductException.class)
    public ResponseEntity<ErrorMessage> handleMethodEmptyProductException(EmptyProductException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErrorMessage error = createErrorResponse(status, ex);
        return ResponseEntity.status(status).body(error);
    }

    private ErrorMessage createErrorResponse(HttpStatus status, Exception ex) {
        return ErrorMessage
                .builder()
                .status(status.value())
                .message(ex.getMessage())
                .timestamp(System.currentTimeMillis())
                .build();
    }

    private ErrorMessage createErrorResponse(HttpStatus status, String message) {
        return ErrorMessage
                .builder()
                .status(status.value())
                .message(message)
                .timestamp(System.currentTimeMillis())
                .build();
    }



}
