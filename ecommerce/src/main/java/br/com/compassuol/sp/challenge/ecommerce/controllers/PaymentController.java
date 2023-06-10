package br.com.compassuol.sp.challenge.ecommerce.controllers;

import br.com.compassuol.sp.challenge.ecommerce.dto.request.PaymentRequestDTO;
import br.com.compassuol.sp.challenge.ecommerce.dto.response.PaymentResponseDTO;
import br.com.compassuol.sp.challenge.ecommerce.service.PaymentService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/v1")
public class PaymentController {
    private PaymentService paymentService;

    @PostMapping("/payments")
    public ResponseEntity<PaymentResponseDTO> createPayment(@Valid @RequestBody PaymentRequestDTO paymentRequestDTO){
        var createdPayment = paymentService.createPayment(paymentRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPayment);
    }
}
