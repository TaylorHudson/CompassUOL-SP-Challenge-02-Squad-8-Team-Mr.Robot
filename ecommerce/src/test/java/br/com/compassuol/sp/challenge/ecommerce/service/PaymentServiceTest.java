package br.com.compassuol.sp.challenge.ecommerce.service;

import br.com.compassuol.sp.challenge.ecommerce.dto.request.PaymentRequestDTO;
import br.com.compassuol.sp.challenge.ecommerce.entity.Customer;
import br.com.compassuol.sp.challenge.ecommerce.entity.Payment;
import br.com.compassuol.sp.challenge.ecommerce.entity.PaymentMethod;
import br.com.compassuol.sp.challenge.ecommerce.entity.Status;
import br.com.compassuol.sp.challenge.ecommerce.repository.OrderRepository;
import br.com.compassuol.sp.challenge.ecommerce.repository.PaymentRepository;
import br.com.compassuol.sp.challenge.ecommerce.utils.CustomerUtil;
import br.com.compassuol.sp.challenge.ecommerce.utils.OrderUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {
    @Mock
    private PaymentRepository paymentRepository;
    @Mock
    private OrderService orderService;
    @Mock
    private CustomerService customerService;



    @Spy
    private ModelMapper mapper;

    @InjectMocks
    private PaymentService service;

    @Test
    void createPayment() {

        var paymentRequestDTO = new PaymentRequestDTO(PaymentMethod.CREDIT_CARD, LocalDate.now(),1);
        var order = OrderUtil.createOrderDefault();
        var customer = CustomerUtil.createExpectedResponseDefault();
        var newPayment = new Payment(1,paymentRequestDTO.getPaymentMethod(),paymentRequestDTO.getPaymentDate(),order);

        when(orderService.findOrderById(anyInt())).thenReturn(order);
        when(customerService.findCustomerById(anyInt())).thenReturn(customer);
        when(paymentRepository.save(any(Payment.class))).thenReturn(newPayment);
        when(orderService.updateStatusOrder(1,Status.CONFIRMED)).thenReturn(order);
        
        var response = service.createPayment(paymentRequestDTO);

        assertEquals(paymentRequestDTO.getPaymentMethod(), response.getPaymentMethod());
        assertEquals(Status.CONFIRMED,order.getStatus());

    }



}