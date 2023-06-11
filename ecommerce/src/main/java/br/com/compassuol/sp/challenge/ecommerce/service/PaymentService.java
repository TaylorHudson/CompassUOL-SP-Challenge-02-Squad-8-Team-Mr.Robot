package br.com.compassuol.sp.challenge.ecommerce.service;

import br.com.compassuol.sp.challenge.ecommerce.dto.request.PaymentRequestDTO;
import br.com.compassuol.sp.challenge.ecommerce.dto.response.PaymentResponseDTO;
import br.com.compassuol.sp.challenge.ecommerce.entity.*;
import br.com.compassuol.sp.challenge.ecommerce.repository.PaymentRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    private final OrderService orderService;

    private final CustomerService customerService;

    private final ModelMapper mapper;

    public PaymentResponseDTO createPayment(PaymentRequestDTO paymentRequestDTO){

        var order = orderService.findOrderById(paymentRequestDTO.getOrderId());
        var customerResponseDTO = customerService.findCustomerById(order.getOrderId());


        var customer = new Customer();
        customer.setCustomerId(customerResponseDTO.getCustomerId());
        customer.setName(customerResponseDTO.getName());
        customer.setCpf(customerResponseDTO.getCpf());
        customer.setEmail(customerResponseDTO.getEmail());
        customer.setActive(true);

        Payment newPayment  = new Payment();
        newPayment.setPaymentDate(paymentRequestDTO.getPaymentDate());
        newPayment.setPaymentMethod(paymentRequestDTO.getPaymentMethod());
        newPayment.setOrder(order);

        newPayment = paymentRepository.save(newPayment);

        orderService.updateStatusOrder(order.getOrderId(),Status.CONFIRMED);



        return new PaymentResponseDTO(newPayment.getPaymentId(), newPayment.getPaymentMethod(),newPayment.getPaymentDate(),order.getOrderId());

    }
}
