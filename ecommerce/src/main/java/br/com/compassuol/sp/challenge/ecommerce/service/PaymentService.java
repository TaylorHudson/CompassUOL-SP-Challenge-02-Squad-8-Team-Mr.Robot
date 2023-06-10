package br.com.compassuol.sp.challenge.ecommerce.service;

import br.com.compassuol.sp.challenge.ecommerce.dto.request.PaymentRequestDTO;
import br.com.compassuol.sp.challenge.ecommerce.dto.response.PaymentResponseDTO;
import br.com.compassuol.sp.challenge.ecommerce.entity.Payment;
import br.com.compassuol.sp.challenge.ecommerce.repository.PaymentRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    private final OrderService orderService;

    private final ModelMapper mapper;

    public PaymentResponseDTO createPayment(PaymentRequestDTO paymentRequestDTO){

        var orderResponseDTO = orderService.findOrderById(paymentRequestDTO.getOrderId());

        Payment newPayment  = mapper.map(paymentRequestDTO,Payment.class);

        newPayment = paymentRepository.save(newPayment);

        //update Order

        return mapper.map(newPayment, PaymentResponseDTO.class);

    }
}
